package com.github.nineteen

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object GptUtils {

    private val CLIENT = OkHttpClient.Builder().build()
    private val REQUEST = Request.Builder().apply {
        this.addHeader("Content-Type", "application/json")
        this.addHeader("Authorization", "Bearer ${getConfig()?.gptConfig?.token}")
        this.url(HTTP_URL)
    }

    private val HTTP_URL = HttpUrl.Builder()
        .scheme("https")
        .host("api.openai.com")
        .addPathSegment("v1")
        .addPathSegment("chat")
        .addPathSegment("completions")
        .build()

    fun completion(content: String) {
        val string = ""
        string.toRequestBody()
        val build = REQUEST
//            .post(RequestBody.)
            .build()
        CLIENT.newCall(build).execute().use { response ->
            {
                val string = response.body?.string()
                println(string)
            }
        }
    }
}