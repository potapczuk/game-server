package com.potapczuk.integration

import com.potapczuk.ServerApplication
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.servlet.http.HttpServletResponse


@RunWith(SpringRunner::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [ServerApplication::class]
)
class PlayerIntegrationTest : AbstractIntegrationTest() {

    @Test
    fun `getPlayer Should return valid player When invoked with correct id`() {
        // GIVEN
        val player = createPlayer(1).save()

        given()
                .withDefaultUser()
        .`when`()
                .get("/v1/player/${player.id}")
        .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("name", equalTo(player.name))
                .body("email", equalTo(player.email))
                .body("score", equalTo(player.score.toInt()))
    }

    @Test
    fun `createPlayer Should create a valid player When invoked with correct information`() {
        // GIVEN
        val player = createPlayerDTO(1)

        given()
                .withDefaultUser()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(player))
        .`when`()
                .post("/v1/player")
        .then()
                .statusCode(HttpServletResponse.SC_CREATED)
                .body("name", equalTo(player.name))
                .body("email", equalTo(player.email))
                .body("score", equalTo(player.score.toInt()))
    }

    @Test
    fun `findPlayers Should return all the players in the system When invoked`() {
        // GIVEN
        createPlayer(1).save()
        createPlayer(2).save()
        createPlayer(3).save()

        given()
                .withDefaultUser()
        .`when`()
                .get("/v1/player")
        .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("size()", `is`(3))
    }

    @Test
    fun `findTop5Players Should return the top 5 players in the system When invoked`() {
        // GIVEN
        createPlayer(1).save()
        createPlayer(2).save()
        createPlayer(3).save()
        createPlayer(4).save()
        createPlayer(5).save()
        createPlayer(6).save()
        createPlayer(7).save()
        createPlayer(8).save()
        createPlayer(9).save()
        createPlayer(10).save()

        given()
                .withDefaultUser()
        .`when`()
                .get("/v1/player/top5")
        .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("size()", `is`(5))
                .body("[0].score", equalTo(110))
    }

    @Test
    fun `deletePlayer Should remove player by id When id is valid`() {
        // GIVEN
        val player = createPlayer(1)
        player.save()

        given()
                .withDefaultUser()
        .`when`()
                .delete("/v1/player/${player.id}")
                .then()
                .statusCode(HttpServletResponse.SC_OK)

        assertThat(playerRepository.findById(player.id).get().deleted).isTrue()
    }
}
