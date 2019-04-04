package com.potapczuk.dataaccessobject

import com.potapczuk.domainobject.UserDO
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * Database Access Object for user table.
 *
 *
 */
interface UserRepository : CrudRepository<UserDO, Long> {

    fun findByUsername(username: String): Optional<UserDO>
}
