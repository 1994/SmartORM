package com.github.nineteen

import java.io.FileInputStream
import java.util.*

data class GptConfig(val token: String)
data class SmartConfig(val gptConfig: GptConfig)

const val CONFIG_FILE = "smartorm.properties"
//fun readFileAsLinesUsingGetResourceAsStream(fileName: String)
//        = SmartConfig::class.java.getResource(fileName)?.readText(Charsets.UTF_8)

inline fun getConfig() : SmartConfig? {
    val rootPath = Thread.currentThread().contextClassLoader.getResource("")?.path
    val p = Properties().apply {
        this.load(FileInputStream("${rootPath}${CONFIG_FILE}"))
    }
    val token = p.getProperty("smartorm.gtp.token") ?: return null
    return SmartConfig(GptConfig(token))
}
