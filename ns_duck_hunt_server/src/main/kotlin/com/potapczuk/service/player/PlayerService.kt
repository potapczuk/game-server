package com.potapczuk.service.player

import com.potapczuk.domainobject.PlayerDO
import com.potapczuk.exception.ConstraintsViolationException
import com.potapczuk.exception.EntityNotFoundException

/**
 * Define the interface for the Player Service operations
 */
interface PlayerService {

    /**
     * Selects a player by id.
     *
     * @param playerId
     * @return found player
     * @throws EntityNotFoundException if no player with the given id was found.
     */
    @Throws(EntityNotFoundException::class)
    fun find(playerId: Long): PlayerDO

    /**
     * Creates a new player.
     *
     * @param playerDO
     * @return
     * @throws ConstraintsViolationException if a player already exists with the given constraints
     */
    @Throws(ConstraintsViolationException::class)
    fun create(playerDO: PlayerDO): PlayerDO

    /**
     * Deletes an existing player by id.
     *
     * @param playerId
     * @throws EntityNotFoundException if no player with the given id was found.
     */
    @Throws(EntityNotFoundException::class)
    fun delete(playerId: Long)

    /**
     * Find all players.
     */
    fun find(): List<PlayerDO>

    fun findTop5(): Collection<PlayerDO>

}
