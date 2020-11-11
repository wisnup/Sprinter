package com.wisnup.sprinter.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties("groupByKey")
data class SprintBugFixContribution(
        val groupByKey: String,
        val key: String, // either sprint or user depends on group by
        val pairing: Set<String>,
        val sprintTotalBugFix: Int
)