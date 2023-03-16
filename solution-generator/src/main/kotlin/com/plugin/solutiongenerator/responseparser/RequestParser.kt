package com.plugin.solutiongenerator.responseparser

import markup.*
import md2html.MarkdownParser
import md2html.StringSource
import java.lang.reflect.Field
import kotlin.math.min


class RequestParser(private var request: String, private val programmingLanguage: String, private val maxWindowWidth: Int) {
    lateinit var parsedRequest: List<Paragraphable>

    companion object {
        public fun deleteFirstLineSeparators(s: String): String {
            var result = s
            while (result.startsWith(System.lineSeparator())) {
                result = result.drop(1)
            }
            return result
        }
    }

    public fun parseRequestToEditorText(): String {
        request = deleteFirstLineSeparators(request)
        parsedRequest = MarkdownParser(StringSource(request)).parse()
        val text = StringBuilder()
        paragraphableListToText(parsedRequest, true, text)
        return text.toString()
    }

    private fun paragraphableListToText(list: List<Paragraphable>, addComments: Boolean, text: StringBuilder) {
        list.forEach { paragraph: Paragraphable ->
                paragraphableToText(paragraph, addComments, text)
        }
    }

    private fun paragraphableToText(paragraph: Paragraphable, addComments: Boolean, text: StringBuilder) {
        var addCommentsTemp = addComments
        if (paragraph is Code) {
            addCommentsTemp = false
        }

        if (paragraph is Text) {
            val sb = StringBuilder()
            paragraph.toMarkdown(sb)
            applyMaxWidthAndAddComments(sb.toString(), addCommentsTemp, text)
            return
        }

        val abstractElement = paragraph as AbstractElement
        paragraphableListToText(
            abstractElement.content as List<Paragraphable>,
            addCommentsTemp,
            text
        )
    }

    private fun applyMaxWidthAndAddComments(s: String, addComments: Boolean, text: StringBuilder) {
        val textLinesWithAppliedMaxWidth = applyMaxWidth(ArrayList(s.split(System.lineSeparator())), addComments)
        textLinesWithAppliedMaxWidth.forEach { line: String ->
            if (addComments) {
                text.append(ProgrammingLanguages.addComments(programmingLanguage, line))
            } else {
                text.append("   $line")
            }
            text.append(System.lineSeparator())
        }
    }

    private fun applyMaxWidth(lines: ArrayList<String>, addComments: Boolean): ArrayList<String> {
        if (!addComments) {
            return lines
        }

        val linesWithAppliedMaxWidth = ArrayList<String>()
        val iterator = lines.listIterator()
        while (iterator.hasNext()) {
            var line = iterator.next()
            while (line.isNotEmpty()) {
                val newLine = applyMaxWidthForLine(line)
                linesWithAppliedMaxWidth.add(newLine)
                line = line.substring(newLine.length)
            }
        }

        return linesWithAppliedMaxWidth;
    }

    private fun applyMaxWidthForLine(line: String): String {
        if (line.length < maxWindowWidth) {
            return line
        }
        val maxLen = min(line.length, maxWindowWidth)
        for (i in (maxLen - 1) downTo 0) {
            if (line[i] == ' ') {
                return line.substring(0, i + 1)
            }
        }
        return line
    }
}