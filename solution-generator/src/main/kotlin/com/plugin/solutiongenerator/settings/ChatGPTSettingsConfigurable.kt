package com.plugin.solutiongenerator.settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.Nullable
import javax.swing.JComponent


class ChatGPTSettingsConfigurable: Configurable {
    private var settingsComponent: ChatGPTSettingsComponent? = null

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName() = "ChatGPT Solution Generator"

    @Nullable
    override fun createComponent(): JComponent? {
        settingsComponent = ChatGPTSettingsComponent()
        return settingsComponent?.getPanel()
    }

    override fun isModified(): Boolean {
        val settings = ChatGPTSettingsState.getInstance()
        return !settings?.token.equals(settingsComponent?.getToken())
    }

    override fun apply() {
        val settings = ChatGPTSettingsState.getInstance()
        settings?.token = settingsComponent?.getToken()!!
    }

    override fun reset() {
        settingsComponent?.setToken(ChatGPTSettingsState.getInstance()?.token!!)
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}