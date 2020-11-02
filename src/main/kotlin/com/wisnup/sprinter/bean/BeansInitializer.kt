package com.wisnup.sprinter.bean

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext

class BeansInitializer: ApplicationContextInitializer<GenericApplicationContext> {

    override fun initialize(applicationContext: GenericApplicationContext) {
        beans().initialize(applicationContext)
    }
}