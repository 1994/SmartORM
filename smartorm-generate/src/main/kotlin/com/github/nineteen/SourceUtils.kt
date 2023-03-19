package com.github.nineteen

import com.google.common.io.Files
import java.io.File
import java.nio.charset.Charset
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.tools.StandardLocation

object SourceUtils {

    fun readSource(element: Element, processorEnv: ProcessingEnvironment): String {
        val path = processorEnv.filer.getResource(
            StandardLocation.SOURCE_PATH,
            processorEnv.elementUtils.getPackageOf(element).toString(),
            "${element.simpleName}.java"
        ).toUri()

        // todo read source from compiler
        return Files.readLines(File(path), Charset.defaultCharset()).joinToString("")
    }
}