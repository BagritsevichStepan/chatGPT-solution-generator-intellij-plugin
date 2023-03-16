package com.plugin.solutiongenerator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.plugin.solutiongenerator.exceptions.*
import com.plugin.solutiongenerator.responseparser.ProgrammingLanguages
import com.plugin.solutiongenerator.responseparser.RequestParser


class GenerateSolutionAction: AnAction() {
    private lateinit var messenger: ChatGPTMessenger
    private lateinit var editor: Editor
    private lateinit var project: Project
    private lateinit var document: Document
    private lateinit var selectedText: String
    private lateinit var fileProgrammingLanguage: String

    companion object {
        private const val TEXT_WIDTH_PERCENTAGE_OF_SCREEN_WIDTH = 70
    }

    override fun update(event: AnActionEvent) {
        val currentProject = event.project;
        event.presentation.isEnabledAndVisible = currentProject != null;
    }

    override fun actionPerformed(event: AnActionEvent) {
        try {
            processEvent(event)
        } catch (e: Exception) {
            ExceptionsHandler.handleException(e)
        }
    }

    private fun processEvent(event: AnActionEvent) {
        editor = event.getRequiredData(CommonDataKeys.EDITOR)
        project = event.getRequiredData(CommonDataKeys.PROJECT)
        document = editor.document

        try {
            fileProgrammingLanguage = ProgrammingLanguages.getProgrammingLanguageFromFileExtension(
                FileDocumentManager.getInstance().getFile(document)?.extension!!
            )!!
        } catch (e: UnsupportedFileExtensionException) {
            Messages.showMessageDialog(
                "The file extension is not supported. Specify in the task the language for the answer from ChatGPT.",
                "Unsupported File Extension",
                Messages.getInformationIcon()
            )
        }

        try {
            selectedText = editor.selectionModel.selectedText!!
        } catch (e: NullPointerException) {
            throw InvalidSelectedTextException()
        }

        messenger = ChatGPTMessenger(selectedText, fileProgrammingLanguage)
        replaceSelectedTextWithResponseText()
    }

    private fun replaceSelectedTextWithResponseText() {
        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd

        WriteCommandAction.runWriteCommandAction(project) {
            document.replaceString(start, end, getResponseText())
        }

        primaryCaret.removeSelection()
    }

    private fun getResponseText(): String {
        val textToParse ="$selectedText\n${RequestParser.deleteFirstLineSeparators(
            messenger.makeRequestAndGetResponse()!!)}"
        val parser = RequestParser(
            textToParse,
            fileProgrammingLanguage,
            convertWidthToCharacters(editor.component.width) / 100 * TEXT_WIDTH_PERCENTAGE_OF_SCREEN_WIDTH
        )
        return parser.parseRequestToEditorText()
    }

    private fun convertWidthToCharacters(width: Int) = width / 7

}