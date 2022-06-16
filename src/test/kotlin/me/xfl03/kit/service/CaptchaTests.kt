package me.xfl03.kit.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import me.xfl03.kit.response.HcaptchaVerifyResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CaptchaTests {
    @Autowired
    lateinit var captchaService: CaptchaService
    val mapper = jacksonObjectMapper()

    @Test
    fun hcaptchaSucceed() {
        assert(captchaService.checkHcaptcha("10000000-aaaa-bbbb-cccc-000000000001"))
    }

    @Test
    fun hcaptchaFailed() {
        assert(!captchaService.checkHcaptcha("10000000-aaaa-bbbb-cccc-000000000003"))
    }

    @Test
    fun jacksonReadJson() {
        val json = """
            {
                "success": true,
                "credit": false,
                "hostname": "dummy-key-pass",
                "challenge_ts": "2022-06-14T19:13:23.758Z"
            }
        """.trimIndent()
        assertDoesNotThrow {
            val obj = mapper.readValue<HcaptchaVerifyResponse>(json)
            assert(obj.success)
        }
    }
}