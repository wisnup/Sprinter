package com.wisnup.sprinter.controller

import com.wisnup.sprinter.usecase.CountSprintPrReview
import com.wisnup.sprinter.usecase.CountSprintStory
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class RestRequestHandler(
        private val countSprintStory: CountSprintStory,
        private val countSprintPrReview: CountSprintPrReview
) {

    suspend fun getStoryContribution(): ServerResponse {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintStory.execute())
    }

    suspend fun getReviewContribution(): ServerResponse {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintPrReview.execute())
    }
}