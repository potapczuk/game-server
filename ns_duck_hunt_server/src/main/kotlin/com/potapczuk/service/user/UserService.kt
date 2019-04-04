package com.potapczuk.service.user

import com.potapczuk.domainobject.UserDO
import com.potapczuk.exception.ConstraintsViolationException
import com.potapczuk.exception.EntityNotFoundException

interface UserService {

    @Throws(EntityNotFoundException::class)
    fun find(userId: Long): UserDO

    @Throws(ConstraintsViolationException::class)
    fun create(userDO: UserDO): UserDO

    @Throws(EntityNotFoundException::class)
    fun delete(userId: Long)

    fun find(): List<UserDO>
}
