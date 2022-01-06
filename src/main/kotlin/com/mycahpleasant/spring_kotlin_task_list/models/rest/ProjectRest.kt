package com.mycahpleasant.spring_kotlin_task_list.models.rest

data class ProjectRest(
    val projectId: Long,
    val name: String,
    val tasks: List<TaskRest> = listOf()
)