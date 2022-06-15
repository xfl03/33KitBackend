package me.xfl03.kit.service

import me.xfl03.kit.config.CdnConfig
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class CdnService {
    @Autowired
    lateinit var cdnConfig: CdnConfig

    fun getAliyunCdnUrl(path: String, timestamp: Long): String {
        val pathFixed = if (path.startsWith("/")) path else "/$path"
        val keys = getAliyunCdnKeys(pathFixed, timestamp)
        return "https://${cdnConfig.aliyunDomain}/${keys.first}/${keys.second}$pathFixed"
    }

    fun getAliyunCdnKeys(path: String, timestamp: Long): Pair<String, String> {
        val pathEncoded = URLEncoder.encode(path, StandardCharsets.UTF_8).replace("%2F", "/")
        val timestampText = timestamp.toString(16).uppercase()
        val text = "${cdnConfig.aliyunKey}$pathEncoded$timestampText"
        println(text)
        val md5 = DigestUtils.md5Hex(text).lowercase()
        return Pair(md5, timestampText)
    }
}