package com.github.nineteen

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit


const val ROLE_USER = "user"
const val ROLE_SYSTEM = "system"
const val ROLE_ASSISTANT = "assistant"

enum class MessageRole(val role: String) {
    USER(ROLE_USER), SYSTEM(ROLE_SYSTEM), ASSISTANT(ROLE_ASSISTANT)
}


data class CompletionMessage(val role: String, val content: String)

class MessageBuilder {
    var messageRole: MessageRole = MessageRole.USER
    var content = ""
    fun build(): CompletionMessage = CompletionMessage(messageRole.role, content)
}

fun message(block: MessageBuilder.() -> Unit): CompletionMessage = MessageBuilder().apply(block).build()

data class CompletionRequest(
    val model: String, val temperature: Double = 0.0, val messages: List<CompletionMessage>
)

data class CompletionResponse(
    val choices: List<CompletionChoice>, val usage: CompletionUsage
)

data class CompletionUsage(val promptTokens: Long, val totalTokens: Long)
data class CompletionChoice(val message: CompletionMessage)

object GptUtils {

    private val JSON = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    private val CONFIG: GptConfig? = getConfig()?.gptConfig

    private val CLIENT = OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES).callTimeout(5, TimeUnit.MINUTES).proxy(CONFIG?.proxy).build()

    private val HTTP_URL =
        HttpUrl.Builder().scheme("https").host("api.openai.com").addPathSegment("v1").addPathSegment("chat")
            .addPathSegment("completions").build()

    private val REQUEST = Request.Builder().apply {
        this.addHeader("Content-Type", "application/json")
        this.addHeader("Authorization", "Bearer ${CONFIG?.token}")
    }

    fun completion(userContent: String, systemContent: String): String? {
        if ((CONFIG == null) || CONFIG.token.isBlank()) {
            return null
        }

        val completionRequest = CompletionRequest(model = CONFIG.model, messages = listOf(message {
            messageRole = MessageRole.USER
            content = userContent
        }, message {
            messageRole = MessageRole.SYSTEM
            content = systemContent
        }))

        val toRequestBody = JSON.toJson(completionRequest).toRequestBody("application/json".toMediaTypeOrNull())
        val build = REQUEST.post(toRequestBody).url(HTTP_URL).build()

        val result: String = CLIENT.newCall(build).execute().use {
                it.body?.string()
            } ?: return null

        val r = JSON.fromJson(result, CompletionResponse::class.java)
        return r.choices[0].message.content
    }
}