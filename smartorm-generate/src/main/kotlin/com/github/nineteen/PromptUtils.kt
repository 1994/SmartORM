package com.github.nineteen

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element

data class GenerateContext(val entity: Element, val dao: Element)

object PromptUtils {

    fun getPrompt(context: GenerateContext, processingEnvironment: ProcessingEnvironment) :String {
        val entitySource = SourceUtils.readSource(context.entity, processingEnvironment)
        val daoSource = SourceUtils.readSource(context.dao, processingEnvironment)
        return "entity class: ${entitySource}\n"
    }
}