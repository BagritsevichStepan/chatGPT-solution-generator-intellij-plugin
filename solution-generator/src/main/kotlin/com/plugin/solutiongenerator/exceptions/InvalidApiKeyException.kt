package com.plugin.solutiongenerator.exceptions

class InvalidApiKeyException(message: String): CustomException(message) {
    constructor(): this("Incorrect API key.")
}