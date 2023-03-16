package com.plugin.solutiongenerator.exceptions

class ResponseException(message: String): CustomException(message) {
    constructor(): this("An error occurred while receiving a response from ChatGPT.")
}