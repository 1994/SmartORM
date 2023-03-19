package com.github.nineteen.processor

import com.github.nineteen.SourceUtils
import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.BasicAnnotationProcessor
import com.google.common.collect.ImmutableSetMultimap
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

const val SMART_DAO = "com.github.nineteen.smartorm.SmartDAO"
@SupportedAnnotationTypes(
    SMART_DAO)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
open class SmartORMProcessor: BasicAnnotationProcessor() {

    override fun steps(): MutableIterable<Step> {
        return mutableListOf(SmartORMStep(processingEnv))
    }

    class SmartORMStep(private val processorEnv: ProcessingEnvironment) : Step {

        override fun annotations(): MutableSet<String> {
            return mutableSetOf(SMART_DAO)
        }

        override fun process(elementsByAnnotation: ImmutableSetMultimap<String, Element>?): MutableSet<out Element> {

            val smartDaos = elementsByAnnotation?.get(SMART_DAO)
            smartDaos?.filter { it.kind == ElementKind.INTERFACE }
                ?.forEach {
                    // todo
                    val allAnnotationMirrors = processorEnv.elementUtils.getAllAnnotationMirrors(it)
                    if (allAnnotationMirrors.size > 0) {
                        val datasourceValue = AnnotationMirrors.getAnnotationValue(allAnnotationMirrors.first(), "datasource")
                        val readSource = SourceUtils.readSource(it, processorEnv)
                    }
                }
            return mutableSetOf()
        }

    }
}