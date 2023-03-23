package com.github.nineteen

import java.io.InputStream
import java.util.*

data class GptConfig(val token: String)
data class SmartConfig(val gptConfig: GptConfig)

const val CONFIG_FILE = "smartorm.properties"

fun getConfig() : SmartConfig? {
    val p = Properties().apply {
        val resourceAsStream: InputStream = SmartConfig::javaClass.javaClass.classLoader.getResourceAsStream(CONFIG_FILE)!!
        this.load(resourceAsStream)
    }
    val token = p.getProperty("smartorm.gpt.token") ?: return null
    return SmartConfig(GptConfig(token))
}
