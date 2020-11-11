package com.wisnup.sprinter.controller

import org.springframework.web.reactive.function.server.coRouter

class RestRouter(private val handler: RestRequestHandler) {

    companion object {
        const val groupByParam = "groupBy"
        const val withLinksParam = "withLinks"
    }

    fun routes() = coRouter {
        "/stories".nest {
            GET("/", handler::getStoryContribution)
        }
        "/reviews".nest {
            GET("/", handler::getPrReviewContribution)
        }
        "/fixes".nest {
            GET("/", handler::getBugFixContribution)
        }
        "/chores".nest {
            GET("/", handler::getChoreContribution)
        }
    }
}