package com.wisnup.sprinter.network

import com.wisnup.sprinter.config.AppConfig
import com.wisnup.sprinter.interceptor.AuthorizationInterceptor
import okhttp3.OkHttpClient

class OkHttpClientConfiguration(
        private val appConfig: AppConfig
) {
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(appConfig.githubToken))
            .build()
}