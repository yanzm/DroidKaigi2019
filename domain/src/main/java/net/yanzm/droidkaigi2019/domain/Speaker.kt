package net.yanzm.droidkaigi2019.domain

data class SpeakerId(val value: String)

data class Speaker(
    val id: SpeakerId,
    val name: String,
    val tagline: String,
    val biography: String,
    val profileIcon: String
)
