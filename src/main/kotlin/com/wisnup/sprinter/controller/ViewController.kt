package com.wisnup.sprinter.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping


@Controller
class ViewController {

    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Sprinter"
        return "main"
    }
}