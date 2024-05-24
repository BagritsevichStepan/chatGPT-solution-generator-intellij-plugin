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

    companion object {
        private const val TEXT_WIDTH_PERCENTAGE_OF_SCREEN_WIDTH = 70
    }

    override fun update(event: AnActionEvent) {
        val currentProject = event.project
        event.presentation.isEnabledAndVisible = currentProject != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        try {
            processEvent(event)
        } catch (e: Exception) {
            ExceptionsHandler.handleException(e)
        }
    }

    private fun processEvent(event: AnActionEvent) {
        val editor = event.getRequiredData(CommonDataKeys.EDITOR)
        val project = event.getRequiredData(CommonDataKeys.PROJECT)
        val document = editor.document

        val extension = FileDocumentManager.getInstance().getFile(document)?.extension
        val fileProgrammingLanguage = if (extension != null) {
            ProgrammingLanguages.getProgrammingLanguageFromFileExtension(extension)
        } else {
            null
        }

        if (fileProgrammingLanguage == null) {
            Messages.showMessageDialog(
                "The file extension $extension is not supported. Specify in the task the language for the answer from ChatGPT.",
                "Unsupported File Extension",
                Messages.getInformationIcon()
            )
        }

        val selectedText = editor.selectionModel.selectedText ?: throw InvalidSelectedTextException()

        val messenger = ChatGPTMessenger(selectedText, fileProgrammingLanguage)

        replaceSelectedTextWithResponseText(
            editor, project, document, messenger, selectedText, fileProgrammingLanguage
        )
    }

    private fun replaceSelectedTextWithResponseText(
        editor: Editor, project: Project, document: Document,
        messenger: ChatGPTMessenger, selectedText: String, fileProgrammingLanguage: String?
    ) {
        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd

        WriteCommandAction.runWriteCommandAction(project) {
            document.replaceString(
                start, end, getResponseText(editor, messenger, selectedText, fileProgrammingLanguage)
            )
        }

        primaryCaret.removeSelection()
    }

    private fun getResponseText(editor: Editor, messenger: ChatGPTMessenger,
                                selectedText: String, fileProgrammingLanguage: String?): String {
        val textToParse ="$selectedText\n${RequestParser.deleteFirstLineSeparators(messenger.makeRequestAndGetResponse())}"
        val parser = RequestParser(
            textToParse,
            fileProgrammingLanguage,
            convertWidthToCharacters(editor.component.width) / 100 * TEXT_WIDTH_PERCENTAGE_OF_SCREEN_WIDTH
        )
        return parser.parseRequestToEditorText()
    }

    private fun convertWidthToCharacters(width: Int) = width / 7

}