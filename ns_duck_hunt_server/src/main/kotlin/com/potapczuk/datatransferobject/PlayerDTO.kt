package com.potapczuk.datatransferobject

import com.fasterxml.jackson.annotation.JsonInclude
import javax.validation.constraints.NotNull

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PlayerDTO(
    val id: Long?,

    @NotNull(message = "name can not be null!")
    val name: String,

    @NotNull(message = "email Plate can not be null!")
    val email: String,

    @NotNull(message = "score count can not be null!")
    val score: Long
)