package me.xfl03.kit

import me.xfl03.kit.service.CdnService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CdnTests {
    @Autowired
    lateinit var cdnService: CdnService
    @Test
    fun asciiPathUrl(){
        println(cdnService.getAliyunCdnUrl("test",1655319593L))
        assert(
            cdnService.getAliyunCdnUrl("test",1655319593L)
                    == "https://33.unijzlsx.com/e28eb832bf28580a85696695d6b0193b/62AA2C29/test"
        )
    }
    @Test
    fun utf8PathUrl(){
        println(cdnService.getAliyunCdnUrl("测试",1655319562L))
        assert(
            cdnService.getAliyunCdnUrl("测试",1655319562L)
                    == "https://33.unijzlsx.com/2393042a9de88567353ab551be4540ac/62AA2C0A/测试"
        )
    }
}