package me.xfl03.kit.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/")
    fun root(): String {
        return "Nothing here."
    }
    @GetMapping("/hw")
    fun helloWorld(): String {
        return "Hello, world!"
    }
}