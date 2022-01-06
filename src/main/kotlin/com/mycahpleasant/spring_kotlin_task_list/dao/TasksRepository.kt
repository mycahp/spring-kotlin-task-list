package com.mycahpleasant.spring_kotlin_task_list.dao

import com.mycahpleasant.spring_kotlin_task_list.models.persistence.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

class TasksRepository@Repository
interface TaskRepository : JpaRepository<Task, Long> {
}