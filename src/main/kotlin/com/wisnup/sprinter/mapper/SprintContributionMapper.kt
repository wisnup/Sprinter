package com.wisnup.sprinter.mapper

import com.github.IssuesQuery
import com.wisnup.sprinter.config.GroupContributionBy
import com.wisnup.sprinter.model.SprintBugFixContribution
import com.wisnup.sprinter.model.SprintChoreContribution
import com.wisnup.sprinter.model.SprintReviewContribution
import com.wisnup.sprinter.model.SprintStoryContribution
import java.util.*
import java.util.regex.Pattern


class SprintContributionMapper {

    fun mapSprintStory(
            groupByKey: String,
            data: IssuesQuery.Data?,
            sprint: String,
            user: String,
            groupBy: GroupContributionBy,
            withLinks: Boolean
    ): SprintStoryContribution {

        var totalStory = 0
        var totalWeight = 0
        val pairingSet = mutableSetOf<String>()
        val linkSet = TreeSet<String>()
        data?.search?.nodes?.forEach { queryData ->
            val issue = queryData?.asIssue
            totalWeight += getWeightFromTitle(issue?.title)
            if (totalWeight > 0) {
                totalStory += 1
                if (withLinks) {
                    linkSet.add(issue?.url.toString())
                }
            }
            issue?.assignees?.nodes?.forEach {
                val assignee = it?.login
                if (assignee != null && assignee != user) {
                    pairingSet.add(assignee)
                }
            }
        }

        return SprintStoryContribution(
                groupByKey = groupByKey,
                key = if (groupBy == GroupContributionBy.SPRINT) {
                    user
                } else sprint,
                pairing = pairingSet,
                sprintTotalIssue = totalStory,
                sprintTotalPoint = totalWeight,
                links = linkSet
        )
    }

    fun mapSprintPrReview(
            groupByKey: String,
            data: IssuesQuery.Data?,
            sprint: String,
            user: String,
            groupBy: GroupContributionBy,
            withLinks: Boolean
    ): SprintReviewContribution {
        var totalPrReview = 0
        val pairingSet = mutableSetOf<String>()
        val linkSet = TreeSet<String>()
        data?.search?.nodes?.forEach { queryData ->
            val issue = queryData?.asIssue
            totalPrReview += 1
            if (withLinks) {
                linkSet.add(issue?.url.toString())
            }
            issue?.assignees?.nodes?.forEach {
                val assignee = it?.login
                if (assignee != null && assignee != user) {
                    pairingSet.add(assignee)
                }
            }
        }

        return SprintReviewContribution(
                groupByKey = groupByKey,
                key = if (groupBy == GroupContributionBy.SPRINT) {
                    user
                } else sprint,
                pairing = pairingSet,
                sprintTotalReview = totalPrReview,
                links = linkSet
        )
    }

    fun mapSprintBugFix(
            groupByKey: String,
            data: IssuesQuery.Data?,
            sprint: String,
            user: String,
            groupBy: GroupContributionBy,
            withLinks: Boolean
    ): SprintBugFixContribution {
        var totalBugFix = 0
        val pairingSet = mutableSetOf<String>()
        val linkSet = TreeSet<String>()
        data?.search?.nodes?.forEach { queryData ->
            val issue = queryData?.asIssue
            totalBugFix += 1
            if (withLinks) {
                linkSet.add(issue?.url.toString())
            }
            issue?.assignees?.nodes?.forEach {
                val assignee = it?.login
                if (assignee != null && assignee != user) {
                    pairingSet.add(assignee)
                }
            }
        }

        return SprintBugFixContribution(
                groupByKey = groupByKey,
                key = if (groupBy == GroupContributionBy.SPRINT) {
                    user
                } else sprint,
                pairing = pairingSet,
                sprintTotalBugFix = totalBugFix,
                links = linkSet
        )
    }

    fun mapSprintChore(
            groupByKey: String,
            data: IssuesQuery.Data?,
            sprint: String,
            user: String,
            groupBy: GroupContributionBy,
            withLinks: Boolean
    ): SprintChoreContribution {
        var totalChore = 0
        val pairingSet = mutableSetOf<String>()
        val linkSet = TreeSet<String>()
        data?.search?.nodes?.forEach { queryData ->
            val issue = queryData?.asIssue
            totalChore += 1
            if (withLinks) {
                linkSet.add(issue?.url.toString())
            }
            issue?.assignees?.nodes?.forEach {
                val assignee = it?.login
                if (assignee != null && assignee != user) {
                    pairingSet.add(assignee)
                }
            }
        }

        return SprintChoreContribution(
                groupByKey = groupByKey,
                key = if (groupBy == GroupContributionBy.SPRINT) {
                    user
                } else sprint,
                pairing = pairingSet,
                sprintTotalChore = totalChore,
                links = linkSet
        )
    }

    private fun getWeightFromTitle(title: String?): Int {
        if (title == null) return 0

        val p = Pattern.compile("\\[[sS][pP]=(.*?)]")
        val m = p.matcher(title)

        return if (m.find()) {
            m.group(1).toInt()
        } else {
            0
        }
    }
}