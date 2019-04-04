package com.potapczuk.controller.mapper

import com.potapczuk.datatransferobject.UserDTO
import com.potapczuk.domainobject.UserDO

object UserMapper {

    fun makeUserDO(userDTO: UserDTO): UserDO {
        return UserDO(userDTO.username, userDTO.password)
    }

    fun makeUserDTO(userDO: UserDO): UserDTO = UserDTO(userDO.id, userDO.username, userDO.password)

    fun makeUserDTOList(users: Collection<UserDO>): List<UserDTO> {
        return users
                .map { makeUserDTO(it) }
    }
}
