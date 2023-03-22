package com.github.nineteen

import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit


const val ROLE_USER = "user"
const val ROLE_SYSTEM = "system"
const val ROLE_ASSISTANT = "assistant"

enum class MessageRole(val role: String) {
    USER(ROLE_USER),
    SYSTEM(ROLE_SYSTEM),
    ASSISTANT(ROLE_ASSISTANT)
}

const val MODEL = "gpt-3.5-turbo"

data class CompletionMessage(val role: String, val content: String)

class MessageBuilder() {
    var messageRole: MessageRole = MessageRole.USER
    var content = ""
    fun build(): CompletionMessage = CompletionMessage(messageRole.role, content)
}

fun message(block: MessageBuilder.() -> Unit): CompletionMessage = MessageBuilder().apply(block).build()

data class CompletionRequest(
    val model: String = MODEL,
    val temperature: Double = 0.0,
    val messages: List<CompletionMessage>
)

object GptUtils {

    private val JSON = Gson()
    private val CLIENT = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .callTimeout(5, TimeUnit.MINUTES)
//        .proxy(Proxy(Proxy.Type.SOCKS, InetSocketAddress("localhost", 7891)))
        .build()

    private val HTTP_URL = HttpUrl.Builder()
        .scheme("https")
        .host("api.openai.com")
        .addPathSegment("v1")
        .addPathSegment("chat")
        .addPathSegment("completions")
        .build()

    private val REQUEST = Request.Builder().apply {
        this.addHeader("Content-Type", "application/json")
        this.addHeader("Authorization", "Bearer ${getConfig()?.gptConfig?.token}")
    }



    fun completion(userContent: String, systemContent: String) {
        val completionRequest = CompletionRequest(messages = listOf(message {
            messageRole = MessageRole.USER
            content = userContent
        }, message {
            messageRole = MessageRole.SYSTEM
            content = systemContent
        }))

        val toRequestBody = JSON.toJson(completionRequest).toRequestBody("application/json".toMediaTypeOrNull())
        val build = REQUEST
            .post(toRequestBody)
            .url(HTTP_URL)
            .build()


        CLIENT.newCall(build).execute().use { response ->
            {
                println(response.isSuccessful)
                val string = response.body?.string()
                println(string)
            }
        }
    }
}