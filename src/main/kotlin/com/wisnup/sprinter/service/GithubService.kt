package com.wisnup.sprinter.service

import com.github.IssuesQuery
import com.github.ReviewQuery
import com.wisnup.sprinter.network.GraphQlClient

class GithubService(private val client: GraphQlClient) {

    suspend fun queryIssue(query: String): IssuesQuery.Data? {
        return client.query(IssuesQuery(query = query))
    }

    suspend fun queryPullRequest(query: String): ReviewQuery.Data? {
        return client.query(ReviewQuery(query = query))
    }
}