package com.plugin.solutiongenerator.responseparser

class ProgrammingLanguages {
    companion object {
        fun getProgrammingLanguageFromFileExtension(extension: String): String? {
            return ProgrammingLanguagesExtensions[extension]
        }

        private val oneLineComments = mapOf(
            "JavaScript" to "//", "C#" to "//", "Go" to "//", "Python" to "#",
            "Ruby" to "#", "C" to "//", "C++" to "//", "PHP" to "//", "Visual Basic" to "‘",
            "Java" to "//", "Kotlin" to "//", "Swift" to "//", "TypeScript" to "//",
            "R" to "#", "Perl" to "#", "Rust" to "//", "Erlang" to "%", "Elixir" to "#",
            "Haskell" to "– –", "Scala" to "//", "Gradle" to "//"
        )
        private val twoLineComments = mapOf(
            "HTML" to listOf("<!--", " -->"), "XML" to listOf("<!-- ", "-->"),
            "CSS" to listOf("/*", "*/"), "Less" to listOf("/*", "*/"),
            "Sass" to listOf("/*", "*/")
        )

        fun addComments(programmingLanguage: String?, text: String): String {
            if (oneLineComments.containsKey(programmingLanguage)) {
                return "${oneLineComments[programmingLanguage]}  $text"
            }
            if (twoLineComments.containsKey(programmingLanguage)) {
                val twoLineComment = twoLineComments[programmingLanguage]!!
                return "${twoLineComment[0]}  $text  ${twoLineComment[1]}"
            }
            return text
        }
    }
}