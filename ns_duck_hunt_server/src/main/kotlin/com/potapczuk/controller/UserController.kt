package com.potapczuk.controller

import com.potapczuk.controller.mapper.UserMapper
import com.potapczuk.datatransferobject.UserDTO
import com.potapczuk.exception.ConstraintsViolationException
import com.potapczuk.exception.EntityNotFoundException
import com.potapczuk.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


/**
 * All operations with a user will be routed by this controller.
 *
 */
@RestController
@RequestMapping("v1/user")
internal class UserController @Autowired
constructor(private val userService: UserService) {


    @GetMapping("/{userId}")
    @Throws(EntityNotFoundException::class)
    fun getUser(@PathVariable userId: Long): UserDTO {
        return UserMapper.makeUserDTO(userService.find(userId))
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Throws(ConstraintsViolationException::class)
    fun createUser(@Valid @RequestBody userDTO: UserDTO): UserDTO {
        val userDO = UserMapper.makeUserDO(userDTO)
        return UserMapper.makeUserDTO(userService.create(userDO))
    }


    @DeleteMapping("/{userId}")
    @Throws(EntityNotFoundException::class)
    fun deleteUser(@PathVariable userId: Long) {
        userService.delete(userId)
    }


    @GetMapping
    fun findUsers(): List<UserDTO> =
            UserMapper.makeUserDTOList(userService.find())
}
