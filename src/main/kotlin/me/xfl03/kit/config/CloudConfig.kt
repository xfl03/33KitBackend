package me.xfl03.kit.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "kit.cloud")
data class CloudConfig(
    val aliyunCdnKey: String,
    val aliyunCdnDomain: String,
    val aliyunAccessKey: String,
    val aliyunAccessSecret: String,
    val aliyunOssBucket: String,
    val aliyunOssEndpoint: String,
)
