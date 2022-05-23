package com.andymcg.northumberlandbadmintonadmin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomePageController {

    @GetMapping("/")
    fun displayHomePage(): String = "home"
}