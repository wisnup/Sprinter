package com.wisnup.sprinter.model

data class SprintBugFixContribution(
        val pairing: Set<String>,
        val sprint: String,
        val sprintTotalBugFix: Int
)