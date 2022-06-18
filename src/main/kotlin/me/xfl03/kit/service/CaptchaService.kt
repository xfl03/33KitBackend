package me.xfl03.kit.service

import me.xfl03.kit.config.CaptchaConfig
import me.xfl03.kit.response.HcaptchaVerifyResponse
import me.xfl03.kit.response.RecaptchaV3VerifyResponse
import me.xfl03.kit.util.post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CaptchaService {
    @Autowired
    lateinit var captchaConfig: CaptchaConfig
    fun checkHcaptcha(token: String): Boolean {
        val res = post<HcaptchaVerifyResponse>(
            "https://hcaptcha.com/siteverify",
            data = mapOf("response" to token, "secret" to captchaConfig.hcaptchaSecret)
        )
        return res?.success ?: false
    }

    fun checkRecaptchaV3(token: String): Boolean {
        val res = post<RecaptchaV3VerifyResponse>(
            "https://www.recaptcha.net/recaptcha/api/siteverify",
            data = mapOf("response" to token, "secret" to captchaConfig.hcaptchaSecret)
        )
        val success = res?.success ?: false
        val score = res?.score ?: 0
        return success && (score.toDouble() >= 0.33)
    }
}