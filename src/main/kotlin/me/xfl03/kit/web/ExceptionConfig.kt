package me.xfl03.kit.web

import me.xfl03.kit.exception.BadRequestException
import me.xfl03.kit.exception.ForbiddenException
import me.xfl03.kit.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionConfig {
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity<ErrorResponse>(
            ErrorResponse(ex.message),
            HttpStatus.FORBIDDEN
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity<ErrorResponse>(
            ErrorResponse(ex.message),
            HttpStatus.BAD_REQUEST
        )
    }
}