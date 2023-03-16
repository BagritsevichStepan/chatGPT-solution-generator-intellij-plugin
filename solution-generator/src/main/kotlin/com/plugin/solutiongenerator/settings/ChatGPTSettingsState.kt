package com.plugin.solutiongenerator.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.NotNull

@State(
    name = "com.plugin.solutiongenerator.settings.ChatGPTSettingsState",
    storages = [Storage("ChatGPTSettingsState.xml")]
)
class ChatGPTSettingsState: PersistentStateComponent<ChatGPTSettingsState> {
    public var token = ""

    companion object {
        public fun getInstance(): ChatGPTSettingsState? {
            return ApplicationManager.getApplication().getService(ChatGPTSettingsState::class.java)
        }
    }

    override fun getState() = this

    override fun loadState(@NotNull state: ChatGPTSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}