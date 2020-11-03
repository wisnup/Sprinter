package com.wisnup.sprinter.model

data class SprintStoryContribution(
        val key: String, // either sprint or user depends on group by
        val pairing: Set<String>,
        val sprintTotalIssue: Int,
        val sprintTotalWeight: Int
)