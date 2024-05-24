package com.plugin.solutiongenerator

import com.plugin.solutiongenerator.exceptions.*
import com.plugin.solutiongenerator.settings.ChatGPTSettingsState
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

class ChatGPTMessenger(private val text: String, private val programmingLanguage: String?) {
    private val client = HttpClient.newHttpClient()

    companion object {
        private const val CHATGPT_URL = "https://api.openai.com/v1/chat/completions"
        private const val INVALID_API_KEY_STATUS_CODE = 401

        private fun getRequestBody(text: String, programmingLanguage: String?): String {
            return "{\"model\": \"gpt-3.5-turbo\", \"messages\":[{\"role\": \"user\", \"content\": \"$text ${
                if (programmingLanguage != null) "using $programmingLanguage" else ""
            }\"}]}"
        }
    }

    fun makeRequestAndGetResponse(): String {
        val response = getResponse()
        val statusCode = response.statusCode()

        if (statusCode == HttpURLConnection.HTTP_OK) {
            return extractTextFromResponseBody(response.body())
        }
        if (statusCode == INVALID_API_KEY_STATUS_CODE) {
            throw InvalidApiKeyException()
        }
        throw ResponseStatusException(statusCode)
    }

    private fun getResponse(): HttpResponse<String> {
        val request = makeRequest()
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
        } catch (e: IOException) {
            throw ResponseException(e)
        } catch (e: InterruptedException) {
            throw ResponseException(e)
        }
    }

    private fun makeRequest(): HttpRequest {
        val token = ChatGPTSettingsState.getInstance()?.token ?: throw RequestException(InvalidApiKeyException("API key is not specified"))
        return HttpRequest.newBuilder()
            .uri(URI(CHATGPT_URL))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $token")
            .POST(convertTextToPostBody())
            .build()
    }

    private fun convertTextToPostBody(): HttpRequest.BodyPublisher {
        val requestBody = getRequestBody(text, programmingLanguage)
        return HttpRequest.BodyPublishers.ofString(JSONObject(requestBody).toString(), StandardCharsets.UTF_8)
    }

    private fun extractTextFromResponseBody(responseBody: String): String {
        try {
            return JSONObject(responseBody)
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
        } catch (e: Exception) {
            throw ResponseParseException(e)
        }
    }
}