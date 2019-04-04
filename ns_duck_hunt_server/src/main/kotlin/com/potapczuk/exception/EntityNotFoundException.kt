package com.potapczuk.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not find entity with id.")
class EntityNotFoundException(message: String) : Exception(message)
