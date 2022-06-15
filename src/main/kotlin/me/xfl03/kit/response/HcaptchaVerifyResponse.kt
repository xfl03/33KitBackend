package me.xfl03.kit.response

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class HcaptchaVerifyResponse(
    var success: Boolean,
    @JsonProperty("challenge_ts")
    var challengeTs: Date?,
    var hostname: String?,
    var credit: Boolean?,
    @JsonProperty("error-codes")
    var errorCodes: Array<String>?,
    var score: Number?,
    @JsonProperty("score_reason")
    var scoreReason: Array<String>?
)
