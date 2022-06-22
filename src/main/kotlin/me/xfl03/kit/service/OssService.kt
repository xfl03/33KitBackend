package me.xfl03.kit.service

import com.aliyun.oss.HttpMethod
import com.aliyun.oss.OSSClientBuilder
import com.aliyun.oss.model.GeneratePresignedUrlRequest
import com.fasterxml.jackson.databind.ObjectMapper
import me.xfl03.kit.config.CloudConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*

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

    fun getSignedUrl(path: String, method: HttpMethod): String {
        val request = GeneratePresignedUrlRequest(bucket, path, HttpMethod.PUT)
        val expiration = Date(Date().time + 3600 * 1000)
        request.expiration = expiration
        request.contentType = "application/octet-stream"
        val signedUrl = ossClient.generatePresignedUrl(request)
        println(signedUrl)
        return signedUrl.toString()
    }
}