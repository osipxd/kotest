package io.kotest.core.extensions

import io.kotest.core.TagExpression
import io.kotest.core.config.Configuration
import io.kotest.core.spec.SpecRef

/**
 * Extension point that allows intercepting execution of projects.
 */
interface ProjectExtension : Extension {

   /**
    * Implementations must invoke the [callback] callback if they wish
    * the project to be executed, otherwise not calling [callback] will skip
    * the entire project.
    *
    * Implementations can modify the [ProjectContext] and changes will be reflected downstream.
    */
   suspend fun interceptProject(context: ProjectContext, callback: suspend (ProjectContext) -> Unit)
}

data class ProjectContext(val tags: TagExpression, val specs: List<SpecRef>, val configuration: Configuration)
