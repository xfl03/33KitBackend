package me.xfl03.kit.controller

import com.aliyun.oss.HttpMethod
import me.xfl03.kit.service.InternalService
import me.xfl03.kit.service.OssService
import me.xfl03.kit.util.getPjskFilename
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class InternalController {
    @Autowired
    lateinit var internalService: InternalService

    @Autowired
    lateinit var ossService: OssService

    @PostMapping("/internal/updatePjsk/{key}/{secret}")
    fun updatePjskDownload(
        @PathVariable key: String,
        @PathVariable secret: String,
        @RequestParam server: String,
        @RequestParam version: String,
        @RequestPart file: MultipartFile?
    ): String {
        internalService.checkAccess(key, secret)
        try {
            if (file != null) {
                ossService.uploadFile(file.inputStream, getPjskFilename(server, version))
            }
            internalService.updatePjskDownload(server, version)
        } catch (e: Exception) {
            return e.toString()
        }
        return "OK"
    }

    @GetMapping("/internal/updatePjskInfo/{key}/{secret}")
    fun updatePjskDownloadInfo(
        @PathVariable key: String,
        @PathVariable secret: String,
        @RequestParam server: String,
        @RequestParam version: String
    ): String {
        return updatePjskDownload(key, secret, server, version, null)
    }

    @GetMapping("/internal/getPjskSignedUrl/{key}/{secret}")
    fun getPjskSignedUrl(
        @PathVariable key: String,
        @PathVariable secret: String,
        @RequestParam server: String,
        @RequestParam version: String
    ): String {
        internalService.checkAccess(key, secret)
        try {
            return ossService.getSignedUrl(getPjskFilename(server, version), HttpMethod.PUT)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}