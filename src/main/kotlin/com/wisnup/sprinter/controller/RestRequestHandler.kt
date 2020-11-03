package com.wisnup.sprinter.controller

import com.wisnup.sprinter.usecase.CountSprintBugFix
import com.wisnup.sprinter.usecase.CountSprintPrReview
import com.wisnup.sprinter.usecase.CountSprintStory
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class RestRequestHandler(
        private val countSprintStory: CountSprintStory,
        private val countSprintPrReview: CountSprintPrReview,
        private val countSprintBugFix: CountSprintBugFix
) {

    suspend fun getStoryContribution(): ServerResponse {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintStory.execute())
    }

    suspend fun getPrReviewContribution(): ServerResponse {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintPrReview.execute())
    }

    suspend fun getBugFixContribution(): ServerResponse {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintBugFix.execute())
    }
}