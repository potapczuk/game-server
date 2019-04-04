package com.potapczuk.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.potapczuk.ServerApplication
import com.potapczuk.dataaccessobject.PlayerRepository
import com.potapczuk.dataaccessobject.UserRepository
import com.potapczuk.datatransferobject.PlayerDTO
import com.potapczuk.datatransferobject.UserDTO
import com.potapczuk.domainobject.PlayerDO
import com.potapczuk.domainobject.UserDO
import com.potapczuk.security.JwtTokenProvider.Companion.BEARER
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = [ServerApplication::class]
)
@TestPropertySource(locations=["classpath:test.properties"])
abstract class AbstractIntegrationTest {
    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var playerRepository: PlayerRepository

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @LocalServerPort
    protected var serverPort: Int = 0

    @Before
    fun setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.port = serverPort
        playerRepository.deleteAll()
        userRepository.deleteAll()
    }

    @After
    fun tearDown() {
        RestAssured.reset()
        playerRepository.deleteAll()
        userRepository.deleteAll()
    }

    protected fun createUser(number: Int) = UserDO("username$number", "username${number}pw").withEncodedPassword()

    protected fun createUserDTO(number: Int): UserDTO = UserDTO(0, "username$number", "password$number")

    protected fun UserDO.withEncodedPassword(): UserDO {
        return apply {
            password = BCryptPasswordEncoder().encode(password)
        }
    }

    protected fun UserDO.save() = apply { userRepository.save(this) }

    protected fun UserDO.getToken(): String {
        return given()
                .contentType(ContentType.JSON)
                .body("""{"username":"${this.username}", "password":"${this.username}pw"}""")
                .`when`()
                .post("/signin")
                .andReturn().jsonPath().getString("token")
    }

    protected fun RequestSpecification.withUser(user: UserDO): RequestSpecification {
        return apply {
            val token = user.getToken()
            this.header(AUTHORIZATION, "$BEARER $token")
        }
    }

    protected fun RequestSpecification.withDefaultUser(): RequestSpecification {
        return apply {
            val token = createUser(998).save().getToken()
            this.header(AUTHORIZATION, "$BEARER $token")
        }
    }

    protected fun createPlayer(number: Int) = PlayerDO(
            "name$number",
            "email$number",
            100L + number)

    protected fun createPlayerDTO(number: Int) = PlayerDTO(
            null,
            "name$number",
            "email$number",
            100L + number
    )

    protected fun PlayerDO.save() = apply { playerRepository.save(this) }
}
