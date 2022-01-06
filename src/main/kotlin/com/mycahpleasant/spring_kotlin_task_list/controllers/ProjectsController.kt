package com.mycahpleasant.spring_kotlin_task_list.controllers

import com.mycahpleasant.spring_kotlin_task_list.dao.ProjectRepository
import com.mycahpleasant.spring_kotlin_task_list.dao.TaskRepository
import com.mycahpleasant.spring_kotlin_task_list.models.persistence.Project
import com.mycahpleasant.spring_kotlin_task_list.models.rest.ProjectRest
import com.mycahpleasant.spring_kotlin_task_list.models.rest.TaskRest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
class ProjectsController @Autowired constructor(
    val projectRepository: ProjectRepository,
    val taskRepository: TaskRepository
) {

    @GetMapping("/projects")
    fun getProjects(): ResponseEntity<List<ProjectRest>> {
        return ResponseEntity<List<ProjectRest>>(projectRepository.findAll().map { it ->
            ProjectRest(
                projectId = it.projectId,
                name = it.name,
                tasks = it.tasks.map {
                    TaskRest(
                        taskId = it.taskId,
                        description = it.description,
                        completed = it.completed
                    )
                }
            )
        }, HttpStatus.OK)
    }

    @GetMapping("/projects/{id}", produces = ["application/json"])
    fun getProjectById(@PathVariable("id") projectId: Long): ResponseEntity<ProjectRest> {
        val project = projectRepository.findById(projectId).orElseThrow {
            IllegalArgumentException("Project not found")
        }

        return ResponseEntity<ProjectRest>(
            ProjectRest(
                projectId = project.projectId,
                name = project.name,
                tasks = project.tasks.map {
                    TaskRest(
                        taskId = it.taskId,
                        description = it.description,
                        completed = it.completed
                    )
                }
            ), HttpStatus.OK
        )
    }

    @PostMapping("/projects", produces = ["application/json"])
    fun createProject(@RequestBody project: Project): ResponseEntity<ProjectRest> {
        val newProject = projectRepository.save(project)

        if (ObjectUtils.isEmpty(newProject)) {
            return ResponseEntity<ProjectRest>(HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity<ProjectRest>(
            ProjectRest(
                projectId = newProject.projectId,
                name = newProject.name,
                tasks = listOf()
            ), HttpStatus.OK
        )
    }

    @PutMapping("/projects/{projectId}/task/{taskId}", produces = ["application/json"])
    fun mapTaskToProject(
        @PathVariable("projectId") projectId: Long,
        @PathVariable("taskId") taskId: Long
    ): ResponseEntity<ProjectRest> {
        val project = projectRepository.findById(projectId).orElseThrow {
            IllegalArgumentException("Project not found")
        }

        val task = taskRepository.findById(taskId).orElseThrow {
            IllegalArgumentException("Task not found")
        }

        project.tasks.add(task)
        projectRepository.save(project)

        return ResponseEntity<ProjectRest>(
            ProjectRest(
                projectId = project.projectId,
                name = project.name,
                tasks = project.tasks.map {
                    TaskRest(
                        taskId = it.taskId,
                        description = it.description,
                        completed = it.completed
                    )
                }), HttpStatus.OK
        )
    }

    @DeleteMapping("/projects/{id}", produces = ["application/json"])
    fun deleteProject(@PathVariable("id") projectId: Long): ResponseEntity<Void> {
        projectRepository.findById(projectId).orElseThrow {
            IllegalArgumentException("Project not found")
        }

        projectRepository.deleteById(projectId)

        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }
}