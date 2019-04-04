package com.potapczuk.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This player is already being used by another driver.")
class CarAlreadyInUseException(message: String) : RuntimeException(message)