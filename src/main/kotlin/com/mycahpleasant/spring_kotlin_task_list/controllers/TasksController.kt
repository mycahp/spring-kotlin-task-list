package com.mycahpleasant.spring_kotlin_task_list.controllers

import com.mycahpleasant.spring_kotlin_task_list.dao.ProjectRepository
import com.mycahpleasant.spring_kotlin_task_list.dao.TaskRepository
import com.mycahpleasant.spring_kotlin_task_list.models.persistence.Task
import com.mycahpleasant.spring_kotlin_task_list.models.rest.ProjectRest
import com.mycahpleasant.spring_kotlin_task_list.models.rest.TaskRest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*

// TODO: Create REST response
// TODO: Create REST request
// TODO: Create services for project (saving, getting, etc)
// TODO: Create services for task (saving, getting, etc)


@RestController
class TasksController @Autowired constructor(
    val taskRepository: TaskRepository,
    val projectRepository: ProjectRepository
) {

    @GetMapping("/tasks", produces = ["application/json"])
    fun getTasks(
        @RequestParam(
            "project",
            required = false
        ) projectId: Optional<Long>
    ): ResponseEntity<List<TaskRest>> {

        val projId = projectId.orElseGet { -1 }

        if (projId > 0) {
            val project = projectRepository.findById(projId).orElseThrow {
                IllegalArgumentException("Project not found")
            }

            return ResponseEntity<List<TaskRest>>(project.tasks.map {
                TaskRest(
                    taskId = it.taskId,
                    description = it.description,
                    completed = it.completed,
                )
            }, HttpStatus.OK)
        } else {
            return ResponseEntity<List<TaskRest>>(taskRepository.findAll().map {
                TaskRest(
                    taskId = it.taskId,
                    description = it.description,
                    completed = it.completed,
                    project = it.project?.let { project ->
                        ProjectRest(
                            projectId = project.projectId,
                            name = project.name
                        )
                    })
        }, HttpStatus.OK)
    }
}

@GetMapping("/tasks/{id}", produces = ["application/json"])
fun getTaskById(@PathVariable("id") taskId: Long): ResponseEntity<TaskRest> {
    val task = taskRepository.findById(taskId).orElseThrow {
        IllegalArgumentException("Project not found")
    }

    return ResponseEntity<TaskRest>(
        TaskRest(
            taskId = task.taskId,
            description = task.description,
            completed = task.completed,
            project = task.project?.let {
                ProjectRest(
                    projectId = it.projectId,
                    name = it.name
                )
            }
        ), HttpStatus.OK
    )
}

@PostMapping("/tasks", produces = ["application/json"])
fun createTask(@RequestBody task: Task): ResponseEntity<TaskRest> {
    val savedTask = taskRepository.save(task)

    return ResponseEntity<TaskRest>(
        TaskRest(
            taskId = savedTask.taskId,
            description = savedTask.description,
            completed = savedTask.completed,
            project = savedTask.project?.let {
                ProjectRest(
                    projectId = it.projectId,
                    name = it.name
                )
            }
        ), HttpStatus.CREATED
    )
}

@PutMapping("/tasks/{id}", produces = ["application/json"])
fun updateTask(@PathVariable("id") taskId: Long, @RequestBody task: Task): ResponseEntity<TaskRest> {
    return taskRepository.findById(taskId).map { taskDetails ->
        val updatedTask: Task = taskDetails.copy(
            description = task.description,
            completed = task.completed
        )
        taskRepository.save(updatedTask)
        ResponseEntity(
            TaskRest(
                taskId = task.taskId,
                description = task.description,
                completed = task.completed,
                project = task.project?.let {
                    ProjectRest(
                        projectId = it.projectId,
                        name = it.name
                    )
                }
            ), HttpStatus.OK
        )
    }.orElseThrow {
        IllegalArgumentException("Project not found")
    }
}

@DeleteMapping("/tasks/{id}", produces = ["application/json"])
fun removeTask(@PathVariable("id") taskId: Long): ResponseEntity<Void> {
    val task = taskRepository.findById(taskId)

    if (task.isPresent) {
        taskRepository.deleteById(taskId)
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }

    return ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR)
}
}