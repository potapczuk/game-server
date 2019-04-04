package com.potapczuk.integration

import com.potapczuk.domainobject.UserDO
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test
import javax.servlet.http.HttpServletResponse


class UserIntegrationTest : AbstractIntegrationTest() {

    @Test
    fun `getUser Should return valid user When invoked with correct id`() {
        // GIVEN
        val user = createUser(1).save()

        given()
                .withUser(user)
        .`when`()
                .get("/v1/user/${user.id}")
        .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("id", equalTo(user.id.toInt()))
                .body("username", equalTo(user.username))
    }

    @Test
    fun `createUser Should create a valid user When invoked with correct information`() {
        // GIVEN
        val user = createUserDTO(1)

        given()
                .withDefaultUser()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(user))
        .`when`()
                .post("/v1/user")
        .then()
                .statusCode(HttpServletResponse.SC_CREATED)
                .body("username", equalTo(user.username))
    }

    @Test
    fun `findUser Should return all the users in the system When invoked with no filter`() {
        // GIVEN
        val user = findUserPrecondition()

        given()
                .withUser(user)
        .`when`()
                .get("/v1/user")
        .then()
                .statusCode(HttpServletResponse.SC_OK)
                .body("size()", `is`(3))
    }

    @Test
    fun `deleteUser Should remove user by id When id is valid`() {
        // GIVEN
        val user = createUser(1)
        user.save()

        given()
                .withDefaultUser()
                .`when`()
                .delete("/v1/user/${user.id}")
                .then()
                .statusCode(HttpServletResponse.SC_OK)

        assertThat(userRepository.findById(user.id).get().deleted).isTrue()
    }

    /**
     *  @return last user created
     */
    private fun findUserPrecondition(): UserDO {
        createUser(1).save()
        createUser(2).save()
        return createUser(3).save()
    }
}
