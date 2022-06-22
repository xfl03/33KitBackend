package me.xfl03.kit.controller

import me.xfl03.kit.config.InternalConfig
import me.xfl03.kit.exception.ForbiddenException
import me.xfl03.kit.service.InternalService
import me.xfl03.kit.service.OssService
import me.xfl03.kit.util.getPjskFilename
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class InternalController {
    @Autowired
    lateinit var internalConfig: InternalConfig

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
        @RequestPart file: MultipartFile
    ): String {
        if (key != internalConfig.accessKey || secret != internalConfig.accessSecret) {
            throw ForbiddenException("access key or secret not correct")
        }
        try {
            ossService.uploadFile(file.inputStream, getPjskFilename(server, version))
            internalService.updatePjskDownload(server, version)
        } catch (e: Exception) {
            return e.toString()
        }
        return "OK"
    }
}