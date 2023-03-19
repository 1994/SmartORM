package com.github.nineteen.processor

import com.google.auto.common.BasicAnnotationProcessor
import com.google.common.collect.ImmutableSetMultimap
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

const val SMART_DAO = "com.github.nineteen.SmartDAO"
@SupportedAnnotationTypes(
    SMART_DAO)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
open class SmartORMProcessor: BasicAnnotationProcessor() {

    override fun steps(): MutableIterable<Step> {
        return mutableListOf(SmartORMStep(processingEnv))
    }

    class SmartORMStep(val processorEnv: ProcessingEnvironment) : Step {

        override fun annotations(): MutableSet<String> {
            return mutableSetOf(SMART_DAO)
        }

        override fun process(elementsByAnnotation: ImmutableSetMultimap<String, Element>?): MutableSet<out Element> {
            val smartDaos = elementsByAnnotation?.get(SMART_DAO)
            smartDaos?.filter { it.kind == ElementKind.INTERFACE }
                ?.forEach {
                    println(it)
                }
            return mutableSetOf()
        }

    }
}