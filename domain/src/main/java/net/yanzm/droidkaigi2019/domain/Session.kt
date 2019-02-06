package net.yanzm.droidkaigi2019.domain

import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class SessionId(val value: String)

data class Session(
    val id: SessionId,
    val title: String,
    val abstract: String,
    val speaker: List<Speaker>,
    val sessionFormat: SessionFormat,
    val language: Language,
    val category: Category,
    val simultaneousInterpretationTarget: Boolean,
    val room: Room,
    val timeAndDate: TimeAndDate
)

enum class SessionFormat {
    MIN_30,
    MIN_50
}

enum class Language {
    JA,
    EN,
    MIX
}

enum class Category {
    XR,
    SECURITY,
    UI_AND_DESIGN,
    DESIGNING_APP_ARCHITECTURE,
    HARDWARE,
    ANDROID_PLATFORMS,
    MAINTENANCE_OPERATIONS_TESTING,
    DEVELOPMENT_PROCESSES,
    ANDROID_FRAMEWORK_AND_JETPACK,
    PRODUCTIVITY_AND_TOOLS,
    CROSS_PLATFORM_DEVELOPMENT,
    OTHER
}

enum class Room {
    HALL_A,
    HALL_B,
    ROOM_1,
    ROOM_2,
    ROOM_3,
    ROOM_4,
    ROOM_5,
    ROOM_6,
    ROOM_7
}

data class TimeAndDate(
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
) {
    val durationMinutes: Long = Duration.between(startTime, endTime).toMinutes()
}
