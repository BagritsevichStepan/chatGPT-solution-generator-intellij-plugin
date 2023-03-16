package com.plugin.solutiongenerator.exceptions

class UnsupportedFileExtensionException: CustomException {
    constructor(extension: String): super("Unknown file extension $extension.")
}