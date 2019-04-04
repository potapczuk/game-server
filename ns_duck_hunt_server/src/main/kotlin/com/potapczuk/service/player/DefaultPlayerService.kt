package com.potapczuk.service.player

import com.google.common.collect.Lists
import com.potapczuk.dataaccessobject.PlayerRepository
import com.potapczuk.domainobject.PlayerDO
import com.potapczuk.exception.ConstraintsViolationException
import com.potapczuk.exception.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for player specific operations.
 * <p/>
 */
@Service
class DefaultPlayerService(private val playerRepository: PlayerRepository) : PlayerService {

    @Throws(EntityNotFoundException::class)
    override fun find(playerId: Long): PlayerDO {
        return findPlayerChecked(playerId)
    }


    @Throws(ConstraintsViolationException::class)
    override fun create(playerDO: PlayerDO): PlayerDO {
        try {
            return playerRepository.save(playerDO)
        } catch (e: DataIntegrityViolationException) {
            LOG.warn("ConstraintsViolationException while creating a player: {}", playerDO, e)
            throw ConstraintsViolationException(e.message ?: "")
        }
    }


    @Transactional
    @Throws(EntityNotFoundException::class)
    override fun delete(playerId: Long) {
        val playerDO = findPlayerChecked(playerId)
        playerDO.deleted = true
    }


    override fun find(): List<PlayerDO> {
        return Lists.newArrayList(playerRepository.findAll())
    }


    @Throws(EntityNotFoundException::class)
    private fun findPlayerChecked(playerId: Long): PlayerDO {
        return playerRepository.findById(playerId)
                .orElseThrow { EntityNotFoundException("Could not find player with id: $playerId") }
    }

    override fun findTop5(): Collection<PlayerDO> {
        return Lists.newArrayList(playerRepository.findTop5ByOrderByScoreDesc())
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(DefaultPlayerService::class.java)
    }
}