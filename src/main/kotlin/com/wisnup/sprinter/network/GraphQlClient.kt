package com.wisnup.sprinter.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException


class GraphQlClient(private val apolloClient: ApolloClient) {

    suspend fun <D : Operation.Data, T, V : Operation.Variables> query(query: Query<D, T, V>): T? {
        val response: Response<T> = apolloClient.query(query).await()
        if (response.hasErrors()) {
            throw ApolloException(response.errors!!.joinToString { error -> error.message })
        }
        return response.data
    }
}