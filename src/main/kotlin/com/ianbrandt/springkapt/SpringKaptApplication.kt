package com.ianbrandt.springkapt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ServiceProperties::class)
class SpringKaptApplication

fun main(args: Array<String>) {
	runApplication<SpringKaptApplication>(*args)
}
