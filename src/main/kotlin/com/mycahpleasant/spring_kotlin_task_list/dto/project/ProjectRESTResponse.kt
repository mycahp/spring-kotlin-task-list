package com.mycahpleasant.spring_kotlin_task_list.dto.project

import com.mycahpleasant.spring_kotlin_task_list.dto.task.TaskRESTResponse

data class ProjectRESTResponse(
    var projectId: Long?,
    val name: String,
    val tasks: List<TaskRESTResponse> = listOf()
)