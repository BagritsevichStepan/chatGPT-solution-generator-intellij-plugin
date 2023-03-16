package com.plugin.solutiongenerator.exceptions

class RequestException(message: String): CustomException(message) {
    constructor(): this("An error occurred during sending the request to ChatGPT.")
}