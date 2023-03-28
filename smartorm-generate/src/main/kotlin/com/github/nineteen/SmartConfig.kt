package com.github.nineteen

import java.io.InputStream
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.Proxy.Type
import java.util.*


data class GptConfig(val token: String, val model: String = "gpt-3.5-turbo", val proxy: Proxy?)
data class DatabaseConfig(val dialect: String)
data class SmartConfig(val gptConfig: GptConfig, val databaseConfig: DatabaseConfig)

const val CONFIG_FILE = "smartorm.properties"

fun getConfig(): SmartConfig? {
    val p = Properties().apply {
        val resourceAsStream: InputStream =
            SmartConfig::javaClass.javaClass.classLoader.getResourceAsStream(CONFIG_FILE)!!
        this.load(resourceAsStream)
    }
    val token = p.getProperty("smartorm.gpt.token") ?: return null
    val model = p.getProperty("smartorm.gpt.model", "gpt-3.5-turbo")
    val proxyStr = p.getProperty("smartorm.gpt.proxy")
    var proxy: Proxy? = null
    if (proxyStr?.isNotBlank() == true) {
        var type: Type = Type.SOCKS
        val proxyType = p.getProperty("smartorm.gpt.proxy.type", "socks")
        when (proxyType) {
            "http" -> {
                type = Type.HTTP
            }

            "socks" -> {
                type = Type.SOCKS
            }
        }
        val split = proxyStr.split(":")
        var port = 80
        if (split.size > 1) {
            port = split[1].toInt()
        }
        proxy = Proxy(type, InetSocketAddress(split[0], port))
    }
    val dialect = p.getProperty("smartorm.dialect", "mysql")
    return SmartConfig(GptConfig(token = token, model = model, proxy = proxy), databaseConfig = DatabaseConfig(dialect))

}
