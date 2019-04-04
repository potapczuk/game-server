package com.potapczuk.domainobject

import org.springframework.format.annotation.DateTimeFormat
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(
    name = "player"
)
class PlayerDO(
        @Column(nullable = false)
        @NotNull(message = "name can not be null!")
        var name: String,

        @Column(nullable = false)
        @NotNull(message = "email Plate can not be null!")
        val email: String,

        @Column(nullable = false)
        @NotNull(message = "score count can not be null!")
        val score: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val dateCreated: ZonedDateTime = ZonedDateTime.now()

    @Column(nullable = false)
    var deleted: Boolean = false
}
