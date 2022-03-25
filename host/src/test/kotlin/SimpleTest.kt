package com.example.host

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.test.DefaultAsserter.assertTrue

class SimpleTest {

    @Test
    fun testSimple() {
        val res = evalFile(File("src/test/resources/test.html.kts"))

        assertTrue(
            "test failed:\n  ${res.reports.joinToString("\n  ") { it.message + if (it.exception == null) "" else ": ${it.exception}" }}",
            res is ResultWithDiagnostics.Success
        )
    }
}