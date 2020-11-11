package com.wisnup.sprinter.controller

import com.wisnup.sprinter.config.GroupContributionBy
import com.wisnup.sprinter.usecase.CountSprintBugFix
import com.wisnup.sprinter.usecase.CountSprintChore
import com.wisnup.sprinter.usecase.CountSprintPrReview
import com.wisnup.sprinter.usecase.CountSprintStory
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull

class RestRequestHandler(
        private val countSprintStory: CountSprintStory,
        private val countSprintPrReview: CountSprintPrReview,
        private val countSprintBugFix: CountSprintBugFix,
        private val countSprintChore: CountSprintChore
) {

    suspend fun getStoryContribution(request: ServerRequest): ServerResponse {
        val groupBy = request.queryParamOrNull(RestRouter.groupByParam) ?: GroupContributionBy.SPRINT.name
        val groupContributionBy = GroupContributionBy.valueOf(groupBy.toUpperCase())
        val withLinks = request.queryParamOrNull(RestRouter.groupByParam)?.toBoolean() ?: false
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintStory.execute(groupContributionBy, withLinks))
    }

    suspend fun getPrReviewContribution(request: ServerRequest): ServerResponse {
        val groupBy = request.queryParamOrNull(RestRouter.groupByParam) ?: GroupContributionBy.SPRINT.name
        val groupContributionBy = GroupContributionBy.valueOf(groupBy.toUpperCase())
        val withLinks = request.queryParamOrNull(RestRouter.groupByParam)?.toBoolean() ?: false
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintPrReview.execute(groupContributionBy, withLinks))
    }

    suspend fun getBugFixContribution(request: ServerRequest): ServerResponse {
        val groupBy = request.queryParamOrNull(RestRouter.groupByParam) ?: GroupContributionBy.SPRINT.name
        val groupContributionBy = GroupContributionBy.valueOf(groupBy.toUpperCase())
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintBugFix.execute(groupContributionBy))
    }

    suspend fun getChoreContribution(request: ServerRequest): ServerResponse {
        val groupBy = request.queryParamOrNull(RestRouter.groupByParam) ?: GroupContributionBy.SPRINT.name
        val groupContributionBy = GroupContributionBy.valueOf(groupBy.toUpperCase())
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValueAndAwait(countSprintChore.execute(groupContributionBy))
    }
}