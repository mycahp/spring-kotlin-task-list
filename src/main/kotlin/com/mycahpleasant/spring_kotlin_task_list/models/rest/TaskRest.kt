package com.mycahpleasant.spring_kotlin_task_list.models.rest

data class TaskRest(
    val taskId: Long,
    val description: String,
    val completed: Boolean = false,
)