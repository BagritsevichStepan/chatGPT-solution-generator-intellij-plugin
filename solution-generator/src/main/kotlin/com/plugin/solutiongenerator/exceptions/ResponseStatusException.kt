package com.plugin.solutiongenerator.exceptions

class ResponseStatusException(message: String): CustomException(message) {
    constructor(responseStatusCode: Int): this("Response status code is $responseStatusCode.")
}