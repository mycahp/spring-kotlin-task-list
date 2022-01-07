package com.mycahpleasant.spring_kotlin_task_list.models.persistence

import javax.persistence.*

@Entity
@Table(name = "TASKS")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = true)
    val taskId: Long? = null,

    @Column(nullable = false)
    val description: String,

    val completed: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "projectId",
        nullable = true
    )
    val project: Project? = null
)