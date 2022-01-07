package com.mycahpleasant.spring_kotlin_task_list.models.rest

data class ProjectRest(
    var projectId: Long,
    val name: String,
    val tasks: List<TaskRest> = listOf()
)