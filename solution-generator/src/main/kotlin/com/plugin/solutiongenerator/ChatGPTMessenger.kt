package com.plugin.solutiongenerator

import java.net.HttpURLConnection
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import com.plugin.solutiongenerator.exceptions.*
import com.plugin.solutiongenerator.settings.ChatGPTSettingsState

class ChatGPTMessenger(private val text: String, private val programmingLanguage: String) {
    private val client = HttpClient.newHttpClient()

    companion object {
        private const val CHATGPT_URL = "https://api.openai.com/v1/chat/completions"
        private const val INVALID_API_KEY_STATUS_CODE = 401
    }

    public fun makeRequestAndGetResponse(): String? {
        val response = getResponse()
        if (response?.statusCode() == HttpURLConnection.HTTP_OK) {
            return extractTextFromResponseBody(response.body())
        }
        if (response?.statusCode() == INVALID_API_KEY_STATUS_CODE) {
            throw InvalidApiKeyException()
        }
        throw ResponseStatusException(response?.statusCode()!!)
    }

    private fun getResponse(): HttpResponse<String>? {
        try {
            return client.send(makeRequest(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
        } catch (e: Exception) {
            throw ResponseException()
        }
    }

    private fun makeRequest(): HttpRequest? {
        try {
            return HttpRequest.newBuilder()
                .uri(URI(CHATGPT_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer ${ChatGPTSettingsState.getInstance()?.token}")
                .POST(convertTextToPostBody())
                .build()
        } catch (e: Exception) {
            throw RequestException()
        }
    }

    private fun convertTextToPostBody(): HttpRequest.BodyPublisher? {
        val requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\":[{\"role\": \"user\", \"content\": \"$text using $programmingLanguage\"}]}"
        return HttpRequest.BodyPublishers.ofString(JSONObject(requestBody).toString(), StandardCharsets.UTF_8)
    }

    private fun extractTextFromResponseBody(responseBody: String): String? {
        try {
            return JSONObject(responseBody)
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
        } catch (e: Exception) {
            throw ResponseParseException()
        }
    }
}