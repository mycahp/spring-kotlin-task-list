package com.mycahpleasant.spring_kotlin_task_list.models.persistence

import javax.persistence.*

@Entity
@Table(name = "TASKS")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val taskId: Long,

    @Column(nullable = false)
    val description: String,

    val completed: Boolean = false,
)