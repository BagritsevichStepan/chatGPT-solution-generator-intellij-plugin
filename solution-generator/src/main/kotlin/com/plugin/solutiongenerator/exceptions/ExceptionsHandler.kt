package com.plugin.solutiongenerator.exceptions

import com.intellij.openapi.ui.Messages

class ExceptionsHandler {
    companion object {
        fun handleException(exception: Exception) {
            Messages.showMessageDialog(
                if (exception is CustomException) exception.message else "",
                "An Error Occurred",
                Messages.getInformationIcon()
            )
        }
    }
}