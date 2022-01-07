package com.mycahpleasant.spring_kotlin_task_list.services

import com.mycahpleasant.spring_kotlin_task_list.dao.ProjectRepository
import com.mycahpleasant.spring_kotlin_task_list.dto.project.ProjectRESTRequest
import com.mycahpleasant.spring_kotlin_task_list.dto.project.ProjectRESTResponse
import com.mycahpleasant.spring_kotlin_task_list.dto.task.TaskRESTResponse
import com.mycahpleasant.spring_kotlin_task_list.models.persistence.Project
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.ObjectUtils
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

@Service
class ProjectService @Autowired constructor(
    private val projectRepository: ProjectRepository,
    val taskService: TaskService
) {

    fun getAllProjects(): List<Project> {
        return projectRepository.findAll()
    }

    fun getProject(id: Long): Project {
        return projectRepository.findById((id)).orElseThrow {
            IllegalArgumentException("Project not found")
        }
    }

    fun saveProject(project: ProjectRESTRequest): Project {
        val newProject = projectRepository.save(
            Project(
                name = project.name
            )
        )

        if (ObjectUtils.isEmpty(newProject)) {
            throw IllegalStateException("Failed to create project")
        }

        return newProject
    }

    fun updateProject(id: Long, project: ProjectRESTRequest): Project {
        return projectRepository.findById(id).map {
            projectRepository.save(
                it.copy(
                    name = it.name
                )
            )
        }.orElseThrow {
            IllegalArgumentException("Project not found")
        }
    }

    fun deleteProject(id: Long) {
        projectRepository.findById(id).orElseThrow {
            IllegalArgumentException("Project not found")
        }

        projectRepository.deleteById(id)
    }

    fun addTaskToProject(projectId: Long, taskId: Long): Project {
        val project = getProject(projectId)

        val task = taskService.getTask(taskId)

        project.tasks.add(task)
        return projectRepository.save(project)
    }

    fun convertToRESTResponse(project: Project): ProjectRESTResponse {
        return ProjectRESTResponse(
            projectId = project.projectId,
            name = project.name,
            tasks = project.tasks.map {
                TaskRESTResponse(
                    taskId = it.taskId,
                    description = it.description,
                    completed = it.completed,
                    project = null
                )
            }
        )
    }
}