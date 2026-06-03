package com.example.pitelemetry

data class Telemetry(
    val temp_c: Double,
    val load: Double,
    val uptime_s: Int,
    val device: String
)