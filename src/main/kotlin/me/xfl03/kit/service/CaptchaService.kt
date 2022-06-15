package me.xfl03.kit.service

import me.xfl03.kit.config.CaptchaConfig
import me.xfl03.kit.response.HcaptchaVerifyResponse
import me.xfl03.kit.util.post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CaptchaService {
    @Autowired
    lateinit var captchaConfig: CaptchaConfig
    fun checkHcaptcha(token: String): Boolean {
        val res = post<HcaptchaVerifyResponse>("https://hcaptcha.com/siteverify",
            data = mapOf("response" to token, "secret" to captchaConfig.hcaptchaSecret)
        )
        return res?.success ?: false
    }
}