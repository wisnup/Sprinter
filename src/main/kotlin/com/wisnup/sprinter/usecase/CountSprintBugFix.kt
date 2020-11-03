package com.wisnup.sprinter.usecase

import com.apollographql.apollo.api.toJson
import com.wisnup.sprinter.config.AppConfig
import com.wisnup.sprinter.mapper.SprintContributionMapper
import com.wisnup.sprinter.model.SprintBugFixContribution
import com.wisnup.sprinter.model.SprintStoryContribution
import com.wisnup.sprinter.service.GithubService
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class CountSprintBugFix (
        private val githubService: GithubService,
        private val appConfig: AppConfig,
        private val sprintMapper: SprintContributionMapper
) {

    suspend fun execute(): Map<String, List<SprintBugFixContribution>> {
        val users = appConfig.userList
        val sprints = appConfig.sprintList
        val bugLabel = appConfig.bugLabel

        // key is user
        val contributionMap = HashMap<String, MutableList<SprintBugFixContribution>>()
        users.forEach { user ->
            sprints.forEach { sprint ->
                val query = "is:issue is:closed assignee:$user closed:${sprint.duration} $bugLabel"
                val result = githubService.query(query)

                result?.let { queryData ->
                    logger.info("COBI bug fixes sprint ${sprint.title} ${queryData.toJson()}")
                    if (contributionMap.containsKey(user)) {
                        // update user key
                        contributionMap[user]?.add(
                                sprintMapper.mapSprintBugFix(queryData, sprint.title, user)
                        )
                    } else {
                        // new user key
                        contributionMap[user] = mutableListOf(
                                sprintMapper.mapSprintBugFix(queryData, sprint.title, user)
                        )
                    }
                }
            } // end each sprint
        } // end each user

        return contributionMap
    }
}