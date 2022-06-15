package me.xfl03.kit.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties(prefix = "kit.captcha")
data class CaptchaConfig(
    val hcaptchaSecret: String = "",
)
