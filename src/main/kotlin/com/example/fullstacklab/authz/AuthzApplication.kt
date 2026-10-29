package com.example.fullstacklab.authz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class AuthzApplication

fun main(args: Array<String>) {
    runApplication<AuthzApplication>(*args)
}
