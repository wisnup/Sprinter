package com.wisnup.sprinter.usecase

import com.apollographql.apollo.api.toJson
import com.wisnup.sprinter.config.AppConfig
import com.wisnup.sprinter.config.GroupContributionBy
import com.wisnup.sprinter.mapper.SprintContributionMapper
import com.wisnup.sprinter.model.SprintStoryContribution
import com.wisnup.sprinter.service.GithubService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

class CountSprintStory(
        private val githubService: GithubService,
        private val appConfig: AppConfig,
        private val sprintMapper: SprintContributionMapper
) {
    private val contributionMap = TreeMap<String, MutableSet<SprintStoryContribution>>()

    suspend fun execute(groupBy: GroupContributionBy, withLinks: Boolean): Map<String, Set<SprintStoryContribution>> {
        val users = appConfig.userList
        val sprints = appConfig.sprintList
        val storyQuery = appConfig.storyQuery

        val deferredList = mutableListOf<Deferred<SprintStoryContribution>>()
        coroutineScope {
            sprints.forEach { sprint ->
                users.forEach { user ->
                    val contribution = async {
                        val query = storyQuery.replace("?user", user).replace("?sprint", sprint.duration)
                        val result = githubService.queryIssue(query)
                        val groupByKey: String = if (groupBy == GroupContributionBy.SPRINT) {
                            sprint.title
                        } else {
                            user
                        }

                        logger.info("COBI story result $query ${sprint.title} ${result?.toJson()}")

                        sprintMapper.mapSprintStory(groupByKey, result, sprint.title, user, groupBy, withLinks)
                    }
                    deferredList.add(contribution)
                } // end each sprint
            } // end each user
        } // end coroutine
        deferredList.awaitAll()
        deferredList.forEach {
            it.await().apply {
                updateMap(this.groupByKey, this)
            }
        }
        return contributionMap
    }

    @Synchronized
    private fun updateMap(key: String, contribution: SprintStoryContribution) {
        if (contributionMap.containsKey(key)) {
            contributionMap[key]?.add(contribution)
        } else {
            contributionMap[key] = mutableSetOf(contribution)
        }
    }
}