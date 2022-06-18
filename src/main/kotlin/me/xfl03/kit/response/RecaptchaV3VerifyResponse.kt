package me.xfl03.kit.response

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class RecaptchaV3VerifyResponse(
    var success: Boolean,
    var score: Number?,
    var action: String,
    @JsonProperty("challenge_ts")
    var challengeTs: Date?,
    var hostname: String?,
    @JsonProperty("error-codes")
    var errorCodes: Array<String>?,
)
