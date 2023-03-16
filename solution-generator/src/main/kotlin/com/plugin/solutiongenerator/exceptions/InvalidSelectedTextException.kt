package com.plugin.solutiongenerator.exceptions

class InvalidSelectedTextException: CustomException {
    constructor(): super("Selected text is empty.")
    constructor(text: String): super("Text \"$text\" is invalid.")
}