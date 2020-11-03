package com.wisnup.sprinter.bean

import com.apollographql.apollo.ApolloClient
import com.wisnup.sprinter.config.AppConfig
import com.wisnup.sprinter.controller.RestRequestHandler
import com.wisnup.sprinter.controller.RestRouter
import com.wisnup.sprinter.mapper.SprintContributionMapper
import com.wisnup.sprinter.network.ApolloClientConfiguration
import com.wisnup.sprinter.network.GraphQlClient
import com.wisnup.sprinter.network.OkHttpClientConfiguration
import com.wisnup.sprinter.service.GithubService
import com.wisnup.sprinter.usecase.CountSprintBugFix
import com.wisnup.sprinter.usecase.CountSprintChore
import com.wisnup.sprinter.usecase.CountSprintPrReview
import com.wisnup.sprinter.usecase.CountSprintStory
import okhttp3.OkHttpClient
import org.springframework.context.support.beans

fun beans() = beans {
    /**
     * Application and Config
     */
    bean<AppConfig>()
    /**
     * GraphQL and network
     */
    bean {
        OkHttpClientConfiguration(ref<AppConfig>())
                .okHttpClient()
    }
    bean {
        ApolloClientConfiguration(
                ref<AppConfig>(),
                ref<OkHttpClient>()
        ).apolloClient()
    }
    bean {
        GraphQlClient(ref<ApolloClient>())
    }
    /**
     * Service and router
     */
    bean {
        RestRouter(ref<RestRequestHandler>()).routes()
    }
    bean {
        RestRequestHandler(
                ref<CountSprintStory>(),
                ref<CountSprintPrReview>(),
                ref<CountSprintBugFix>(),
                ref<CountSprintChore>()
        )
    }
    bean {
        SprintContributionMapper()
    }
    bean {
        GithubService(ref<GraphQlClient>())
    }

    /**
     * Use cases
     */
    bean {
        CountSprintStory(ref<GithubService>(), ref<AppConfig>(), ref<SprintContributionMapper>())
    }
    bean {
        CountSprintPrReview(ref<GithubService>(), ref<AppConfig>(), ref<SprintContributionMapper>())
    }
    bean {
        CountSprintBugFix(ref<GithubService>(), ref<AppConfig>(), ref<SprintContributionMapper>())
    }
    bean {
        CountSprintChore(ref<GithubService>(), ref<AppConfig>(), ref<SprintContributionMapper>())
    }
}