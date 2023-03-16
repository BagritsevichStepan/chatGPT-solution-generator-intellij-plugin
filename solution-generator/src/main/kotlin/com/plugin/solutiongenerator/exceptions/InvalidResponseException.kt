package com.plugin.solutiongenerator.exceptions

class InvalidResponseException(message: String): CustomException(message) {
    constructor(): this("Response is empty.")
}