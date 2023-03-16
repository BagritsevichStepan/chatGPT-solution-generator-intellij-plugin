package com.plugin.solutiongenerator.responseparser

import com.plugin.solutiongenerator.exceptions.UnsupportedFileExtensionException

class ProgrammingLanguages {
    companion object {
        public fun getProgrammingLanguageFromFileExtension(extension: String): String? {
            if (ProgrammingLanguagesExtensions.programmingLanguagesExtensions
                    .containsKey(extension)) {
                return ProgrammingLanguagesExtensions.programmingLanguagesExtensions[extension]
            }
            throw UnsupportedFileExtensionException(extension)
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

        public fun addComments(programmingLanguage: String, text: String): String {
            if (oneLineComments.containsKey(programmingLanguage)) {
                return "${oneLineComments[programmingLanguage]}  $text"
            }
            if (twoLineComments.containsKey(programmingLanguage)) {
                return "${twoLineComments[programmingLanguage]?.get(0)}  $text  ${twoLineComments[programmingLanguage]?.get(1)}"
            }
            return text
        }
    }
}