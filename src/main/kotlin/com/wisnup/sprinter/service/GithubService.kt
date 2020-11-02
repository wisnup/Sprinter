package com.wisnup.sprinter.service

import com.github.IssuesQuery
import com.wisnup.sprinter.network.GraphQlClient

class GithubService(private val client: GraphQlClient) {

    suspend fun query(query: String): IssuesQuery.Data? {
        return client.query(IssuesQuery(query = query))
    }
}