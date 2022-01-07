package com.mycahpleasant.spring_kotlin_task_list.services

import com.mycahpleasant.spring_kotlin_task_list.dao.TaskRepository
import com.mycahpleasant.spring_kotlin_task_list.dto.project.ProjectRESTResponse
import com.mycahpleasant.spring_kotlin_task_list.dto.task.TaskRESTRequest
import com.mycahpleasant.spring_kotlin_task_list.dto.task.TaskRESTResponse
import com.mycahpleasant.spring_kotlin_task_list.models.persistence.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class TaskService @Autowired constructor(
    private val taskRepository: TaskRepository,
    private val projectService: ProjectService
) {
    fun getAllTasks(): MutableSet<Task> {
        return taskRepository.findAll().toMutableSet()
    }

    fun getAllTasksByProject(projectId: Long): MutableSet<Task> {
        return projectService.getProject(projectId).tasks
    }

    fun getTask(id: Long): Task {
        return taskRepository.getById(id)
    }

    fun createTask(task: TaskRESTRequest): Task {
        return taskRepository.save(Task(
            description = task.description,
            completed = false
        ))
    }

    fun updateTask(id: Long, task: TaskRESTRequest): Task {
        return taskRepository.findById(id).map {
            taskRepository.save(it.copy(
                description = task.description,
                completed = task.completed
            ))
        }.orElseThrow {
            IllegalArgumentException("Task not found")
        }
    }

    fun deleteTask(id: Long) {
        taskRepository.findById(id).orElseThrow {
            IllegalArgumentException("Task not found")
        }

        taskRepository.deleteById(id)
    }

    fun convertToRESTResponse(task: Task): TaskRESTResponse {
        return TaskRESTResponse(
            taskId = task.taskId,
            description = task.description,
            completed = task.completed,
            project = task.project?.let {
                ProjectRESTResponse(
                    projectId = it.projectId,
                    name = it.name
                )
            }
        )
    }
}