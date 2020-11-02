package com.wisnup.sprinter.model

data class SprintStoryContribution(
        val pairing: Set<String>,
        val sprint: String,
        val sprintTotalIssue: Int,
        val sprintTotalWeight: Int
)