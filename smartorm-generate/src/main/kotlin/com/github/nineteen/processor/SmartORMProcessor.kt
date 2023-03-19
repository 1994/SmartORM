package com.github.nineteen.processor

import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@SupportedAnnotationTypes(
    "com.github.nineteen.SmartDAO")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
open class SmartORMProcessor: AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        println("123123123123123123123123123213")
        if (roundEnv!!.processingOver()) {
            return false
        }


        return true
    }
}