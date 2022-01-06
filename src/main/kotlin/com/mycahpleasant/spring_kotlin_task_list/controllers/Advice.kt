package com.mycahpleasant.spring_kotlin_task_list.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

@ControllerAdvice
class Advice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFoundException(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity<String>(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleInternalErrorException(ex: IllegalStateException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity<String>(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyException(ex: Exception, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity<String>(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}