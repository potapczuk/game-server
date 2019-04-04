package com.potapczuk.controller.mapper

import com.potapczuk.datatransferobject.PlayerDTO
import com.potapczuk.domainobject.PlayerDO

object PlayerMapper {
    fun makePlayerDO(playerDTO: PlayerDTO): PlayerDO {
        return PlayerDO(
                playerDTO.name,
                playerDTO.email,
                playerDTO.score
        )
    }


    fun makePlayerDTO(playerDO: PlayerDO): PlayerDTO {
        return PlayerDTO(
                id = playerDO.id,
                name = playerDO.name,
                email = playerDO.email,
                score = playerDO.score
        )
    }


    fun makePlayerDTOList(players: Collection<PlayerDO>): List<PlayerDTO> {
        return players.map { makePlayerDTO(it) }
    }
}
