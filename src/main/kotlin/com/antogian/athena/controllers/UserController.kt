package com.antogian.athena.controllers

import com.antogian.athena.dto.entities.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.WebRequest


@Controller
@Scope("session")
class UserController

    @Autowired
    constructor(){

    @GetMapping("/register")
    fun register(request: WebRequest?, model: Model): String? {
        val userDto = UserDto()
        model.addAttribute("user", userDto)
        return "register"
    }

}
