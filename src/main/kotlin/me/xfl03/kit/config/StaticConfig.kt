package me.xfl03.kit.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

const val pjskDownloadFile = "pjsk-download.json"

val jsonMapper = ObjectMapper().registerKotlinModule()