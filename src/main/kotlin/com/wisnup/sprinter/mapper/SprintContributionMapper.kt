package com.wisnup.sprinter.mapper

import com.github.IssuesQuery
import com.wisnup.sprinter.model.SprintBugFixContribution
import com.wisnup.sprinter.model.SprintReviewContribution
import com.wisnup.sprinter.model.SprintStoryContribution
import java.util.regex.Pattern


class SprintContributionMapper {

    fun mapSprintStory(data: IssuesQuery.Data, sprint: String, user: String): SprintStoryContribution {
        var totalStories = 0
        var totalWeight = 0
        val pairingSet = mutableSetOf<String>()
        data.search.nodes?.forEach { queryData ->
            val issue = queryData?.asIssue
            totalWeight += getWeightFromTitle(issue?.title)
            if (totalWeight > 0) {
                totalStories += 1
            }
            issue?.assignees?.nodes?.forEach {
                val assignee = it?.login
                if (assignee != null && assignee != user) {
                    pairingSet.add(assignee)
                }
            }
        }

        return SprintStoryContribution(
                pairing = pairingSet,
                sprint = sprint,
                sprintTotalIssue = totalStories,
                sprintTotalWeight = totalWeight
        )
    }

    fun mapSprintPrReview(data: IssuesQuery.Data, sprint: String, user: String): SprintReviewContribution {
        var totalReviews = 0
        val pairingSet = mutableSetOf<String>()
        data.search.nodes?.forEach { queryData ->
            val issue = queryData?.asIssue
            totalReviews += 1
            issue?.assignees?.nodes?.forEach {
                val assignee = it?.login
                if (assignee != null && assignee != user) {
                    pairingSet.add(assignee)
                }
            }
        }

        return SprintReviewContribution(
                pairing = pairingSet,
                sprint = sprint,
                sprintTotalReview = totalReviews
        )
    }

    fun mapSprintBugFix(data: IssuesQuery.Data, sprint: String, user: String): SprintBugFixContribution {
        var bugFixes = 0
        val pairingSet = mutableSetOf<String>()
        data.search.nodes?.forEach { queryData ->
            val issue = queryData?.asIssue
            bugFixes += 1
            issue?.assignees?.nodes?.forEach {
                val assignee = it?.login
                if (assignee != null && assignee != user) {
                    pairingSet.add(assignee)
                }
            }
        }

        return SprintBugFixContribution(
                pairing = pairingSet,
                sprint = sprint,
                sprintTotalBugFix = bugFixes
        )
    }

    private fun getWeightFromTitle(title: String?): Int {
        if (title == null) return 0

        val p = Pattern.compile("\\[SP=(.*?)]")
        val m = p.matcher(title)

        return if (m.find()) {
            m.group(1).toInt()
        } else {
            0
        }
    }
}