package com.wisnup.sprinter.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("config")
class AppConfig {
    lateinit var githubUrl: String
    lateinit var githubToken: String
    lateinit var userList: List<String>
    lateinit var sprintList: List<Sprint>
    lateinit var bugLabel: String
    lateinit var choreLabel: String
}