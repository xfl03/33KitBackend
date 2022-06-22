package me.xfl03.kit.service

import me.xfl03.kit.config.jsonMapper
import me.xfl03.kit.entity.PjskDownloadInfo
import me.xfl03.kit.exception.ForbiddenException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class InternalServiceTests {
    @Autowired
    lateinit var internalService: InternalService

    @Test
    fun checkAccessSuccess() {
        assertDoesNotThrow {
            internalService.checkAccess("foo", "bar")
        }
    }

    @Test
    fun checkAccessFailed() {
        assertThrows<ForbiddenException> {
            internalService.checkAccess("bar", "foo")
        }
    }

    @Test
    fun jackson() {
        val json =
            """
            [{
                "server": "jp",
                "name": "日服",
                "version": "2.1.0",
                "filename": "pjsk_jp_2.1.0.apk",
                "lastUpdate": 1655396417931
            }]
            """.trimIndent()
        assertDoesNotThrow {
            jsonMapper.readValue(json, Array<PjskDownloadInfo>::class.java)
        }
    }
}