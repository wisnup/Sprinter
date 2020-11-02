package com.wisnup.sprinter.network

import com.apollographql.apollo.ApolloClient
import com.wisnup.sprinter.config.AppConfig
import okhttp3.OkHttpClient

class ApolloClientConfiguration(
        private val config: AppConfig,
        private val okHttpClient: OkHttpClient
) {
    fun apolloClient(): ApolloClient = ApolloClient.builder()
            .serverUrl(config.githubUrl)
            .okHttpClient(okHttpClient)
            .build()
}