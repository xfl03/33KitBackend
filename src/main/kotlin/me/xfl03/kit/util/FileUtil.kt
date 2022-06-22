package me.xfl03.kit.util

fun isPathContainsDir(path: String) = path.contains("/")

fun isMobilePackageFile(path: String) = path.endsWith(".apk") || path.endsWith(".ipa")

fun getPjskFilename(server: String, version: String) = "pjsk_${server}_${version}.apk"