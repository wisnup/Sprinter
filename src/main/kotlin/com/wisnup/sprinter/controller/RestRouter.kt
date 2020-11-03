package com.wisnup.sprinter.controller

import org.springframework.web.reactive.function.server.coRouter

class RestRouter(private val handler: RestRequestHandler) {

    fun routes() = coRouter {
        "/stories".nest {
            GET("/") { handler.getStoryContribution() }
        }
        "/reviews".nest {
            GET("/") { handler.getPrReviewContribution() }
        }
    }
}