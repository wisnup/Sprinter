package com.wisnup.sprinter.usecase

import com.apollographql.apollo.api.toJson
import com.wisnup.sprinter.config.AppConfig
import com.wisnup.sprinter.mapper.SprintContributionMapper
import com.wisnup.sprinter.model.SprintReviewContribution
import com.wisnup.sprinter.service.GithubService
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class CountSprintPrReview(
        private val githubService: GithubService,
        private val appConfig: AppConfig,
        private val sprintMapper: SprintContributionMapper
) {

    suspend fun execute(): Map<String, List<SprintReviewContribution>> {
        val users = appConfig.userList
        val sprints = appConfig.sprintList

        // key is user
        val contributionMap = HashMap<String, MutableList<SprintReviewContribution>>()
        users.forEach { user ->
            sprints.forEach { sprint ->
                val query = "is:pr is:closed assignee:$user merged:${sprint.duration}"
                val result = githubService.query(query)

                result?.let { queryData ->
                    logger.info("COBI review sprint ${sprint.title} ${queryData.toJson()}")
                    if (contributionMap.containsKey(user)) {
                        // update user key
                        contributionMap[user]?.add(
                                sprintMapper.mapSprintPrReview(queryData, sprint.title, user)
                        )
                    } else {
                        // new user key
                        contributionMap[user] = mutableListOf(
                                sprintMapper.mapSprintPrReview(queryData, sprint.title, user)
                        )
                    }
                }
            } // end each sprint
        } // end each user

        return contributionMap
    }
}