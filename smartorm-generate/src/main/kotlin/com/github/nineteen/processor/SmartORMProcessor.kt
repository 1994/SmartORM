package com.github.nineteen.processor

import com.google.auto.common.BasicAnnotationProcessor
import com.google.common.collect.ImmutableSetMultimap
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element

@SupportedAnnotationTypes(
    "com.github.nineteen.SmartDAO")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
open class SmartORMProcessor: BasicAnnotationProcessor() {

    override fun steps(): MutableIterable<Step> {
        return mutableListOf(SmartORMStep(processingEnv))
    }

    class SmartORMStep(val processorEnv: ProcessingEnvironment) : Step {

        override fun annotations(): MutableSet<String> {
            return mutableSetOf("com.github.nineteen.SmartDAO")
        }

        override fun process(elementsByAnnotation: ImmutableSetMultimap<String, Element>?): MutableSet<out Element> {
            elementsByAnnotation?.forEach { t, u -> println("${t}${u}") }
            return mutableSetOf()
        }

    }
}