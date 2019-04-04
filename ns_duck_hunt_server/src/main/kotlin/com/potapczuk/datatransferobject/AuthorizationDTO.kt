package com.potapczuk.datatransferobject

import javax.validation.constraints.NotNull

data class AuthorizationDTO(
    @NotNull(message = "username can not be null!")
    val username: String,

    @NotNull(message = "password can not be null!")
    val password: String
)