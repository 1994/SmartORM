package com.github.nineteen

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element

data class PromptContext(
    val smartConfig: SmartConfig,
    val dao: Element,
    val entity: Element,
    val processingEnvironment: ProcessingEnvironment,
    val datasource: String,
)

object PromptUtils {


    fun getPrompt(context: PromptContext): String {
        return "Please generate a Spring JDBC implementation for the ${context.dao.simpleName} interface based on the JPA specification. The implementation should use a ${context.smartConfig.databaseConfig.dialect} database and the datasource bean named '${context.datasource}' for connecting to the database.\n" +
                "Here are the source codes for the ${context.dao.simpleName} interface and the corresponding entity class: The `${context.dao.simpleName}` source code: ${
                    SourceUtils.readSource(
                        context.dao,
                        processorEnv = context.processingEnvironment
                    )
                }\n" +
                "It's entity class `${context.entity.simpleName}` source code: ${
                    SourceUtils.readSource(
                        context.entity,
                        processorEnv = context.processingEnvironment
                    )
                }\n" +
                "Please return only the generated Java code for the ${context.dao.simpleName} implementation without any explanation or comments."
    }

}