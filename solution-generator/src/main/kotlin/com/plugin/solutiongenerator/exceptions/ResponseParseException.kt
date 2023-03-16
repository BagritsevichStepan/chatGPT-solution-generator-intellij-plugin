package com.plugin.solutiongenerator.exceptions

class ResponseParseException(message: String): CustomException(message) {
    constructor(): this("An error occurred while parsing a response from ChatGPT.")
}