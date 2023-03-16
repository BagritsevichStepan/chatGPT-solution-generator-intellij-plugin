package com.plugin.solutiongenerator.exceptions

class UnsupportedFileExtensionException(extension: String) : CustomException("Unknown file extension $extension.") {
}