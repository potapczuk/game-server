package com.potapczuk.controller

import com.potapczuk.datatransferobject.AuthorizationDTO
import com.potapczuk.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @PostMapping("/signin")
    fun login(@RequestBody authorization: AuthorizationDTO): ResponseEntity<Map<String, String>> {
        try {
            val username = authorization.username
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, authorization.password))
            val token = jwtTokenProvider.createToken(username, listOf("ROLE_USER"))

            val model: Map<String, String> = mapOf(
                    "username" to username,
                    "token" to token
            )
            return ok(model)
        } catch (e: AuthenticationException) {
            throw BadCredentialsException("Invalid username/password supplied")
        }
    }
}
