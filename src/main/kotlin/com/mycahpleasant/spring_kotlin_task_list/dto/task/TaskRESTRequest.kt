package com.mycahpleasant.spring_kotlin_task_list.dto.task

data class TaskRESTRequest (
    val description: String,
    val completed: Boolean = false
)