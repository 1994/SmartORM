package com.github.nineteen.processor

import com.github.nineteen.*
import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.BasicAnnotationProcessor
import com.google.common.collect.ImmutableSetMultimap
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

const val SMART_DAO = "com.github.nineteen.smartorm.SmartDAO"

@SupportedAnnotationTypes(
    SMART_DAO
)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
open class SmartORMProcessor : BasicAnnotationProcessor() {

    override fun steps(): MutableIterable<Step> {
        return mutableListOf(SmartORMStep(processingEnv))
    }

    class SmartORMStep(private val processorEnv: ProcessingEnvironment) : Step {

        override fun annotations(): MutableSet<String> {
            return mutableSetOf(SMART_DAO)
        }

        override fun process(elementsByAnnotation: ImmutableSetMultimap<String, Element>?): MutableSet<out Element> {
            val smartDaos = elementsByAnnotation?.get(SMART_DAO)
            smartDaos?.filter { it.kind == ElementKind.INTERFACE && processorEnv.elementUtils.getAllAnnotationMirrors(it).size > 0 }
                ?.forEach {
                    val allAnnotationMirrors = processorEnv.elementUtils.getAllAnnotationMirrors(it)
                    val datasourceValue =
                        AnnotationMirrors.getAnnotationValue(allAnnotationMirrors.first(), "datasource")
                    val entityValue =
                        AnnotationMirrors.getAnnotationValue(allAnnotationMirrors.first(), "entity")

                    val entity = processorEnv.elementUtils.getTypeElement(entityValue.value.toString())

                    val prompt = PromptUtils.getPrompt(
                        PromptContext(
                            getConfig()!!,
                            it,
                            entity,
                            processorEnv,
                            datasourceValue.toString()
                        )
                    )
                    val completion = GptUtils.completion("", prompt)

                    val javaFile = completion?.substringAfter("```java\n")?.substringBefore("```")
                    val packageName = processorEnv.elementUtils.getPackageOf(it).toString()

                    if (javaFile?.isNotBlank() == true) {
                        processorEnv.filer.createSourceFile("${packageName}.${it.simpleName}Impl").openWriter()
                            .use {
                                it.write(javaFile)
                            }
                    }
                }
            return mutableSetOf()
        }

    }
}