package me.xfl03.kit.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CdnTests {
    @Autowired
    lateinit var cdnService: CdnService
    @Test
    fun asciiPathUrl(){
        assert(
            cdnService.getAliyunCdnUrl("test",1655361560L)
                    == "https://example.com/1c40da6d306971341d24eb337c68974d/62AAD018/test"
        )
    }
    @Test
    fun utf8PathUrl(){
        assert(
            cdnService.getAliyunCdnUrl("测试",1655361592L)
                    == "https://example.com/0b597ddc18b8288e7c84005452e7f901/62AAD038/测试"
        )
    }
}