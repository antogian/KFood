package com.antogian.athena.controllers

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
@Scope("session")
class HomeController {
    @GetMapping("/", "/home")
    fun home(): String {
        //HttpSession session = request.getSession();
//        request.session.removeAttribute("shoppingCart")

        return "home"
    }

    @GetMapping("/login")
    fun login(): String {
        //HttpSession session = request.getSession();
//        request.session.removeAttribute("shoppingCart")

        return "login"
    }

//    @GetMapping("/menu-dev")
//    fun menuProd(request: HttpServletRequest): String {
//        //HttpSession session = request.getSession();
//        request.session.removeAttribute("shoppingCart")
//
//        return "menu-prod"
//    }

    //    @RequestMapping("/register")
    //    public String register()
    //    {
    //        //HttpSession session = request.getSession();
    //
    //        return "register";
    //    }

}