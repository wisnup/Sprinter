package com.wisnup.sprinter.model

data class SprintBugFixContribution(
        val key: String, // either sprint or user depends on group by
        val pairing: Set<String>,
        val sprintTotalBugFix: Int
)