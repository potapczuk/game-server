package com.potapczuk.datatransferobject

import com.fasterxml.jackson.annotation.JsonInclude
import javax.validation.constraints.NotNull

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDTO(
        val id: Long?,

        @NotNull(message = "username can not be null!")
        val username: String,

        @NotNull(message = "password can not be null!")
        val password: String
)