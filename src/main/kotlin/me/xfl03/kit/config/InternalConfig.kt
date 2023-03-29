package me.xfl03.kit.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "kit.internal")
data class InternalConfig @ConstructorBinding constructor(
    val accessKey: String,
    val accessSecret: String,
)
