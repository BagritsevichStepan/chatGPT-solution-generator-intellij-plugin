package com.plugin.solutiongenerator.exceptions

abstract class CustomException(message: String): Exception(message)

class InvalidApiKeyException(message: String): CustomException(message) {
    constructor(): this("Incorrect API key.")
}

class InvalidSelectedTextException: CustomException("Selected text is empty.")

class RequestException(message: String): CustomException(message) {
    constructor(e: Exception): this("An error \"${e.message}\" occurred during sending the request to ChatGPT.")
}

class ResponseException(message: String): CustomException(message) {
    constructor(e: Exception): this("An error \"${e.message}\" occurred while receiving a response from ChatGPT.")
}


class ResponseParseException(message: String): CustomException(message) {
    constructor(e: Exception): this("An error \"${e.message}\" occurred while parsing a response from ChatGPT.")
}

class ResponseStatusException(message: String): CustomException(message) {
    constructor(responseStatusCode: Int): this("Response status code is $responseStatusCode.")
}

class UnsupportedFileExtensionException(extension: String) : CustomException("Unknown file extension $extension.")