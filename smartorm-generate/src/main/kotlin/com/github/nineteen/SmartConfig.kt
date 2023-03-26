package com.github.nineteen

import java.io.InputStream
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.Proxy.Type
import java.util.*


const val MODEL = "gpt-3.5-turbo"

data class GptConfig(val token: String, val model: String = MODEL, val proxy: Proxy?)
data class SmartConfig(val gptConfig: GptConfig)

const val CONFIG_FILE = "smartorm.properties"

fun getConfig(): SmartConfig? {
    val p = Properties().apply {
        val resourceAsStream: InputStream =
            SmartConfig::javaClass.javaClass.classLoader.getResourceAsStream(CONFIG_FILE)!!
        this.load(resourceAsStream)
    }
    val token = p.getProperty("smartorm.gpt.token") ?: return null
    val model = p.getProperty("smartorm.gpt.model", MODEL)
    val proxyStr = p.getProperty("smartorm.gpt.proxy")
    var proxy: Proxy? = null
    if (proxyStr?.isNotBlank() == true) {
        var type: Type = Type.SOCKS
        val proxyType = p.getProperty("smartorm.gpt.proxy.type")
        when (proxyType) {
            "http" -> {
                type = Type.HTTP
            }

            "socks" -> {
                type = Type.SOCKS
            }
        }
        val split = proxyType.split(":")
        var port = 80
        if (split.size > 1) {
            port = split[1].toInt()
        }
        proxy = Proxy(type, InetSocketAddress(split[0], port))
    }
    return SmartConfig(GptConfig(token = token, model = model, proxy = proxy))

}
