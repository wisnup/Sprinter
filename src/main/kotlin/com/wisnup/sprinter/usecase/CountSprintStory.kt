package com.wisnup.sprinter.usecase

import com.apollographql.apollo.api.toJson
import com.wisnup.sprinter.config.AppConfig
import com.wisnup.sprinter.mapper.SprintContributionMapper
import com.wisnup.sprinter.model.SprintStoryContribution
import com.wisnup.sprinter.service.GithubService
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class CountSprintStory (
        private val githubService: GithubService,
        private val appConfig: AppConfig,
        private val sprintMapper: SprintContributionMapper
) {

    suspend fun execute(): Map<String, List<SprintStoryContribution>> {
        val users = appConfig.userList
        val sprints = appConfig.sprintList

        // key is user
        val contributionMap = HashMap<String, MutableList<SprintStoryContribution>>()
        users.forEach { user ->
            sprints.forEach { sprint ->
                val query = "is:issue is:closed assignee:$user closed:${sprint.duration}"
                val result = githubService.query(query)

                result?.let { queryData ->
                    logger.info("COBI story sprint ${sprint.title} ${queryData.toJson()}")
                    if (contributionMap.containsKey(user)) {
                        // update user key
                        contributionMap[user]?.add(
                                sprintMapper.mapSprintStory(queryData, sprint.title, user)
                        )
                    } else {
                        // new user key
                        contributionMap[user] = mutableListOf(
                                sprintMapper.mapSprintStory(queryData, sprint.title, user)
                        )
                    }
                }
            } // end each sprint
        } // end each user

        return contributionMap
    }
}