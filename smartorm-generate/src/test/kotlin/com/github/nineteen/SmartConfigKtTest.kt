package com.github.nineteen

import org.junit.jupiter.api.Test

class SmartConfigKtTest {

    @Test
    fun get() {
        val c = getConfig()
        assert(c?.gptConfig?.token.equals("demo.token.asd"))
    }
}