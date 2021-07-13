package com.ianbrandt.springkapt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "service")
data class ServiceProperties(val host: String, val port: Int)
