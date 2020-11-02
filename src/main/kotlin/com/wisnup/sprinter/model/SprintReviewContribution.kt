package com.wisnup.sprinter.model

data class SprintReviewContribution(
        val pairing: Set<String>,
        val sprint: String,
        val sprintTotalReview: Int
)