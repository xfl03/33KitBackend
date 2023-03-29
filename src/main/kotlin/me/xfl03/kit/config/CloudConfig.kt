package me.xfl03.kit.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "kit.cloud")
data class CloudConfig @ConstructorBinding constructor(
    val aliyunCdnKey: String,
    val aliyunCdnDomain: String,
    val aliyunAccessKey: String,
    val aliyunAccessSecret: String,
    val aliyunOssBucket: String,
    val aliyunOssEndpoint: String,
)
