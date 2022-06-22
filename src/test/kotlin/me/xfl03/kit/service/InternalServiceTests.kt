package me.xfl03.kit.service

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
    fun checkAccessSuccess(){
        assertDoesNotThrow {
            internalService.checkAccess("foo","bar")
        }
    }
    @Test
    fun checkAccessFailed(){
        assertThrows<ForbiddenException> {
            internalService.checkAccess("bar","foo")
        }
    }
}