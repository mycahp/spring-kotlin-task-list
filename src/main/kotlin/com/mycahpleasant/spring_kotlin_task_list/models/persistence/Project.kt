package com.mycahpleasant.spring_kotlin_task_list.models.persistence

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
@Table(name = "PROJECTS")
data class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val projectId: Long,

    @Column(unique = true, nullable = false)
    val name: String,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "PROJECT_TASK",
        joinColumns = [JoinColumn(name = "projectId")],
        inverseJoinColumns = [JoinColumn(name = "taskId")]
    )
    @JsonBackReference
    val tasks: MutableSet<Task> = hashSetOf()
)