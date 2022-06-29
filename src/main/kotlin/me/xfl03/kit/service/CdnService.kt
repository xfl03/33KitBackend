package me.xfl03.kit.service

import me.xfl03.kit.config.CloudConfig
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.servlet.view.RedirectView
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class CdnService {
    @Autowired
    lateinit var cloudConfig: CloudConfig

    fun getAliyunCdnUrl(path: String, timestamp: Long = System.currentTimeMillis() / 1000): String {
        val pathFixed = if (path.startsWith("/")) path else "/$path"
        val keys = getAliyunCdnKeys(pathFixed, timestamp)
        return "https://${cloudConfig.aliyunCdnDomain}/${keys.first}/${keys.second}$pathFixed"
    }

    fun getAliyunCdnKeys(path: String, timestamp: Long): Pair<String, String> {
        val pathEncoded = URLEncoder.encode(path, StandardCharsets.UTF_8).replace("%2F", "/")
        val timestampText = timestamp.toString(16).uppercase()
        val text = "${cloudConfig.aliyunCdnKey}$pathEncoded$timestampText"
        val md5 = DigestUtils.md5Hex(text).lowercase()
        return Pair(md5, timestampText)
    }

    fun redirectTo(path: String): RedirectView {
        val ret = RedirectView(getAliyunCdnUrl(path))
        ret.setStatusCode(HttpStatus.TEMPORARY_REDIRECT)
        return ret
    }
}