package com.wisnup.sprinter.usecase

import com.apollographql.apollo.api.toJson
import com.wisnup.sprinter.config.AppConfig
import com.wisnup.sprinter.config.GroupContributionBy
import com.wisnup.sprinter.mapper.SprintContributionMapper
import com.wisnup.sprinter.model.SprintStoryContribution
import com.wisnup.sprinter.service.GithubService
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

class CountSprintStory (
        private val githubService: GithubService,
        private val appConfig: AppConfig,
        private val sprintMapper: SprintContributionMapper
) {

    suspend fun execute(groupBy: GroupContributionBy): Map<String, List<SprintStoryContribution>> {
        val users = appConfig.userList
        val sprints = appConfig.sprintList

        val contributionMap = TreeMap<String, MutableList<SprintStoryContribution>>()
        users.forEach { user ->
            sprints.forEach { sprint ->
                val query = "is:issue is:closed assignee:$user closed:${sprint.duration}"
                val result = githubService.query(query)
                val key: String = if (groupBy == GroupContributionBy.SPRINT) {
                    sprint.title
                } else {
                    user
                }

                result?.let { queryData ->
                    logger.info("COBI story sprint ${sprint.title} ${queryData.toJson()}")
                    if (contributionMap.containsKey(key)) {
                        // update user key
                        contributionMap[key]?.add(
                                sprintMapper.mapSprintStory(queryData, sprint.title, user, groupBy)
                        )
                    } else {
                        // new user key
                        contributionMap[key] = mutableListOf(
                                sprintMapper.mapSprintStory(queryData, sprint.title, user, groupBy)
                        )
                    }
                }
            } // end each sprint
        } // end each user

        return contributionMap
    }
}