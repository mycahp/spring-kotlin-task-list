package com.mycahpleasant.spring_kotlin_task_list.controllers

import com.mycahpleasant.spring_kotlin_task_list.dto.project.ProjectRESTRequest
import com.mycahpleasant.spring_kotlin_task_list.dto.project.ProjectRESTResponse
import com.mycahpleasant.spring_kotlin_task_list.services.ProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProjectsController @Autowired constructor(
    val projectService: ProjectService
) {

    @GetMapping("/projects")
    fun getProjects(): ResponseEntity<List<ProjectRESTResponse>> {
        return ResponseEntity<List<ProjectRESTResponse>>(projectService.getAllProjects().map {
            projectService.convertToRESTResponse(it)
        }, HttpStatus.OK)
    }

    @GetMapping("/projects/{id}", produces = ["application/json"])
    fun getProjectById(@PathVariable("id") projectId: Long): ResponseEntity<ProjectRESTResponse> {
        return ResponseEntity<ProjectRESTResponse>(
            projectService.convertToRESTResponse(
                projectService.getProject(projectId)
            ), HttpStatus.OK
        )
    }

    @PostMapping("/projects", produces = ["application/json"])
    fun createProject(@RequestBody project: ProjectRESTRequest): ResponseEntity<ProjectRESTResponse> {
        return ResponseEntity<ProjectRESTResponse>(
            projectService.convertToRESTResponse(
                projectService.saveProject(project)
            ), HttpStatus.OK
        )
    }

    @PutMapping("/projects/{projectId}")
    fun updateProject(
        @PathVariable("projectId") projectId: Long,
        @RequestBody project: ProjectRESTRequest
    ): ResponseEntity<ProjectRESTResponse> {
        return ResponseEntity<ProjectRESTResponse>(
            projectService.convertToRESTResponse(
                projectService.updateProject(projectId, project)
            ), HttpStatus.OK
        )
    }

    @PutMapping("/projects/{projectId}/task/{taskId}", produces = ["application/json"])
    fun mapTaskToProject(
        @PathVariable("projectId") projectId: Long,
        @PathVariable("taskId") taskId: Long
    ): ResponseEntity<ProjectRESTResponse> {
        return ResponseEntity<ProjectRESTResponse>(
            projectService.convertToRESTResponse(projectService.addTaskToProject(projectId, taskId)), HttpStatus.OK
        )
    }

    @DeleteMapping("/projects/{id}", produces = ["application/json"])
    fun deleteProject(@PathVariable("id") projectId: Long): ResponseEntity<Void> {
        projectService.deleteProject(projectId)

        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }
}