package com.mycahpleasant.spring_kotlin_task_list.controllers

import com.mycahpleasant.spring_kotlin_task_list.dto.task.TaskRESTRequest
import com.mycahpleasant.spring_kotlin_task_list.dto.task.TaskRESTResponse
import com.mycahpleasant.spring_kotlin_task_list.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class TasksController @Autowired constructor(
    val taskService: TaskService
) {

    @GetMapping("/tasks")
    fun getTasks(
        @RequestParam(
            "project",
            required = false
        ) projectId: Optional<Long>
    ): ResponseEntity<List<TaskRESTResponse>> {

        val projId = projectId.orElseGet { -1 }

        val tasks = if (projId > 0) {
            taskService.getAllTasksByProject(projId)
        } else {
            taskService.getAllTasks()
        }

        return ResponseEntity<List<TaskRESTResponse>>(tasks.map {
            taskService.convertToRESTResponse(it)
        }, HttpStatus.OK)
    }

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable("id") taskId: Long): ResponseEntity<TaskRESTResponse> {
        return ResponseEntity<TaskRESTResponse>(
            taskService.convertToRESTResponse(taskService.getTask(taskId)), HttpStatus.OK
        )
    }

    @PostMapping("/tasks")
    fun createTask(@RequestBody task: TaskRESTRequest): ResponseEntity<TaskRESTResponse> {
        return ResponseEntity<TaskRESTResponse>(
            taskService.convertToRESTResponse(taskService.createTask(task)), HttpStatus.CREATED
        )
    }

    @PutMapping("/tasks/{id}")
    fun updateTask(
        @PathVariable("id") taskId: Long,
        @RequestBody task: TaskRESTRequest
    ): ResponseEntity<TaskRESTResponse> {
        return ResponseEntity(
            taskService.convertToRESTResponse(taskService.updateTask(taskId, task)), HttpStatus.OK
        )
    }


    @DeleteMapping("/tasks/{id}")
    fun removeTask(@PathVariable("id") taskId: Long): ResponseEntity<Void> {
        taskService.deleteTask(taskId)
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)

    }
}