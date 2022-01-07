package com.mycahpleasant.spring_kotlin_task_list.dto.task

import com.mycahpleasant.spring_kotlin_task_list.dto.project.ProjectRESTResponse

data class TaskRESTResponse(
    val taskId: Long?,
    val description: String,
    val completed: Boolean = false,
    val project: ProjectRESTResponse? = null
)