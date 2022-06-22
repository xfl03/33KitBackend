package me.xfl03.kit.entity

data class PjskDownloadInfo(
    val server: String,
    val name: String,
    var version: String,
    var filename: String,
    var lastUpdate: Long
)
