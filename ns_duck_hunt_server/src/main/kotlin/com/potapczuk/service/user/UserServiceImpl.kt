package com.potapczuk.service.user

import com.google.common.collect.Lists
import com.potapczuk.dataaccessobject.UserRepository
import com.potapczuk.domainobject.UserDO
import com.potapczuk.exception.ConstraintsViolationException
import com.potapczuk.exception.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some user specific things.
 *
 *
 */
@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : UserService {

    /**
     * Selects a user by id.
     *
     * @param userId
     * @return found user
     * @throws EntityNotFoundException if no user with the given id was found.
     */
    @Throws(EntityNotFoundException::class)
    override fun find(userId: Long): UserDO {
        return findUserChecked(userId)
    }


    /**
     * Creates a new user.
     *
     * @param userDO
     * @return
     * @throws ConstraintsViolationException if a user already exists with the given username, ... .
     */
    @Throws(ConstraintsViolationException::class)
    override fun create(userDO: UserDO): UserDO {
        try {
            userDO.password = passwordEncoder.encode(userDO.password)
            return userRepository.save(userDO)
        } catch (e: DataIntegrityViolationException) {
            LOG.warn("ConstraintsViolationException while creating a user: {}", userDO, e)
            throw ConstraintsViolationException(e.message ?: "")
        }
    }


    /**
     * Deletes an existing user by id.
     *
     * @param userId
     * @throws EntityNotFoundException if no user with the given id was found.
     */
    @Transactional
    @Throws(EntityNotFoundException::class)
    override fun delete(userId: Long) {
        val userDO = findUserChecked(userId)
        userDO.deleted = true
    }


    @Throws(EntityNotFoundException::class)
    private fun findUserChecked(userId: Long): UserDO {
        return userRepository.findById(userId)
                .orElseThrow { EntityNotFoundException("Could not find user with id: " + userId!!) }
    }

    override fun find(): List<UserDO> {
        return Lists.newArrayList(userRepository.findAll())
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }
}
