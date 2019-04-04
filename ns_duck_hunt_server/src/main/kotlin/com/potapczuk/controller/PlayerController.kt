package com.potapczuk.controller

import com.potapczuk.controller.mapper.PlayerMapper
import com.potapczuk.datatransferobject.PlayerDTO
import com.potapczuk.exception.ConstraintsViolationException
import com.potapczuk.exception.EntityNotFoundException
import com.potapczuk.service.player.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * All operations with a player will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/player")
@CrossOrigin
class PlayerController(@Autowired private val playerService: PlayerService) {


    @GetMapping("/{playerId}")
    @Throws(EntityNotFoundException::class)
    fun getPlayer(@PathVariable playerId: Long): PlayerDTO =
            PlayerMapper.makePlayerDTO(playerService.find(playerId))


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Throws(ConstraintsViolationException::class)
    fun createPlayer(@Valid @RequestBody playerDTO: PlayerDTO): PlayerDTO {
        val playerDO = PlayerMapper.makePlayerDO(playerDTO)
        return PlayerMapper.makePlayerDTO(playerService.create(playerDO))
    }


    @DeleteMapping("/{playerId}")
    @Throws(EntityNotFoundException::class)
    fun deletePlayer(@PathVariable playerId: Long) =
            playerService.delete(playerId)


    @GetMapping
    fun findPlayers(): List<PlayerDTO> =
            PlayerMapper.makePlayerDTOList(playerService.find())

    @GetMapping("/top5")
    fun findTop5Players(): List<PlayerDTO> =
            PlayerMapper.makePlayerDTOList(playerService.findTop5())
}