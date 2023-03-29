package me.xfl03.kit.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding


@ConfigurationProperties(prefix = "kit.captcha")
data class CaptchaConfig @ConstructorBinding constructor(
    val hcaptchaSecret: String,
    val recaptchaSecret: String,
    val recaptchaScoreThreshold: Number
)
