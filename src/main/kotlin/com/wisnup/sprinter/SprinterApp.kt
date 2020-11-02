package com.wisnup.sprinter

import com.wisnup.sprinter.bean.BeansInitializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties
@SpringBootApplication
class SprinterApp

fun main(args: Array<String>) {
    SpringApplicationBuilder(SprinterApp::class.java)
            .initializers(BeansInitializer())
            .run(*args)
}
