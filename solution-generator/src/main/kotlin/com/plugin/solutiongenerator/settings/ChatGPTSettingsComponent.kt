package com.plugin.solutiongenerator.settings

import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel
import javax.swing.JPasswordField

class ChatGPTSettingsComponent {
    private var mainPanel: JPanel? = null
    private var token = JPasswordField()

    init {
        token.text = ChatGPTSettingsState.getInstance()?.token
        mainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("API key: "), token, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    fun getPanel() = mainPanel

    fun setToken(newToken: String) {
        token.text = newToken
    }

    fun getToken(): String = token.text
}