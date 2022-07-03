package me.xfl03.kit.util

fun isNaturalNumber(text: String) = try {
    val id = text.toLong()
    id >= 0
} catch (_: Exception) {
    false
}