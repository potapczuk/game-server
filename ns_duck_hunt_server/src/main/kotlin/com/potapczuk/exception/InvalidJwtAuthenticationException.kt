package com.potapczuk.exception

import org.springframework.security.core.AuthenticationException

class InvalidJwtAuthenticationException(message: String) : AuthenticationException(message)