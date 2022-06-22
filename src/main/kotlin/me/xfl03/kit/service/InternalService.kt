package me.xfl03.kit.service

import me.xfl03.kit.config.InternalConfig
import me.xfl03.kit.config.pjskDownloadFile
import me.xfl03.kit.entity.PjskDownloadInfo
import me.xfl03.kit.exception.ForbiddenException
import me.xfl03.kit.util.getPjskFilename
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InternalService {
    @Autowired
    lateinit var ossService: OssService
    @Autowired
    lateinit var internalConfig: InternalConfig

    fun updatePjskDownload(server: String, version: String) {
        val info = ossService.downloadObject(pjskDownloadFile, Array<PjskDownloadInfo>::class.java)
        info
            .filter { it.server == server }
            .forEach {
                it.version = version
                it.filename = getPjskFilename(server, version)
                it.lastUpdate = System.currentTimeMillis()
            }
        ossService.uploadObject(info, pjskDownloadFile)
    }

    fun checkAccess(key: String, secret: String) {
        if (key != internalConfig.accessKey || secret != internalConfig.accessSecret) {
            throw ForbiddenException("access key or secret not correct")
        }
    }
}