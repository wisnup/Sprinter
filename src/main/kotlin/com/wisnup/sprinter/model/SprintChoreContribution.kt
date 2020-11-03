package com.wisnup.sprinter.model

data class SprintChoreContribution(
        val key: String, // either sprint or user depends on group by
        val pairing: Set<String>,
        val sprintTotalChore: Int
)