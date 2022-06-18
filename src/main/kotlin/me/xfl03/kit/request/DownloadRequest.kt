package me.xfl03.kit.request

data class DownloadRequest(
    val filename: String,
    val token: String?,
    val recaptchaToken: String?,
)
