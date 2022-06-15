package me.xfl03.kit.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

val okHttpClient = OkHttpClient()
val jacksonMapper = jacksonObjectMapper()

inline fun <reified T> post(url: String, data: Map<String, String>): T? {
    val formBody = FormBody.Builder()
    for ((k, v) in data) {
        formBody.add(k, v)
    }
    val post = formBody.build()
    val request = Request.Builder()
        .url(url)
        .post(post)
        .build()
    okHttpClient.newCall(request).execute().use { response ->
        if (!response.isSuccessful) return null
        val body = response.body?.string() ?: return null
        return parseJson(body)
    }
}

inline fun <reified T> parseJson(json: String): T? {
    return jacksonMapper.readValue(json)
}