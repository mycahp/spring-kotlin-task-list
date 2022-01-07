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

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val tasks: MutableSet<Task> = hashSetOf()
)