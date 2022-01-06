package com.mycahpleasant.spring_kotlin_task_list.dao

import com.mycahpleasant.spring_kotlin_task_list.models.persistence.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

class ProjectsRepository@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
}