package com.potapczuk.domainobject

import org.springframework.format.annotation.DateTimeFormat
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(
        name = "user",
        uniqueConstraints = [UniqueConstraint(name = "uc_username", columnNames = ["username"])]
)
class UserDO(
        @Column(nullable = false)
        @NotNull(message = "username can not be null!")
        var username: String,

        @Column(nullable = false)
        @NotNull(message = "password Plate can not be null!")
        var password: String
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
