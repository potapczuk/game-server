package com.potapczuk.controller

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class HomeController {

    @RequestMapping("/")
    fun home(): String {
        return "redirect:swagger-ui.html"
    }

    @RequestMapping("/generatePassword/{password}")
    @ResponseBody
    fun generatePassword(@PathVariable password: String): String {
        return BCryptPasswordEncoder().encode(password)
    }
}