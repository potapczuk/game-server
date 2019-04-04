package com.potapczuk.dataaccessobject

import com.potapczuk.domainobject.PlayerDO
import org.springframework.data.repository.CrudRepository

/**
 * Database Access Object for player table.
 */
interface PlayerRepository : CrudRepository<PlayerDO, Long> {
    fun findTop5ByOrderByScoreDesc(): List<PlayerDO>
}