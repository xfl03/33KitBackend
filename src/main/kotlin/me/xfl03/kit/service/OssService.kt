package me.xfl03.kit.service

import com.aliyun.oss.OSSClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import me.xfl03.kit.config.CloudConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class OssService {
    @Autowired
    lateinit var cloudConfig: CloudConfig

    private val ossClient by lazy {
        OSSClientBuilder().build(
            cloudConfig.aliyunOssEndpoint,
            cloudConfig.aliyunAccessKey,
            cloudConfig.aliyunAccessSecret
        )
    }

    private val bucket by lazy {
        cloudConfig.aliyunOssBucket
    }

    private val jsonMapper = ObjectMapper()

    fun uploadFile(`is`: InputStream, path: String) {
        ossClient.putObject(bucket, path, `is`)
    }

    fun <T> uploadObject(obj: T, path: String) {
        val `is` = jsonMapper.writeValueAsBytes(obj).inputStream()
        uploadFile(`is`, path)
    }

    fun <T> downloadObject(path: String, type: Class<T>): T {
        val obj = ossClient.getObject(bucket, path)
        return jsonMapper.readValue(obj.objectContent, type)
    }
}