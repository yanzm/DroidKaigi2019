package net.yanzm.droidkaigi2019

import androidx.annotation.StringRes
import net.yanzm.droidkaigi2019.domain.Category
import net.yanzm.droidkaigi2019.domain.Language
import net.yanzm.droidkaigi2019.domain.Room
import net.yanzm.droidkaigi2019.domain.SessionFormat
import net.yanzm.droidkaigi2019.domain.TimeAndDate

@get:StringRes
val SessionFormat.text: Int
    get() = when (this) {
        SessionFormat.MIN_30 -> R.string.minutes_30
        SessionFormat.MIN_50 -> R.string.minutes_50
    }

@get:StringRes
val Language.text: Int
    get() = when (this) {
        Language.JA -> R.string.language_ja
        Language.EN -> R.string.language_en
        Language.MIX -> R.string.language_mix
    }

@get:StringRes
val Category.text: Int
    get() = when (this) {
        Category.XR -> R.string.category_xr
        Category.SECURITY -> R.string.category_security
        Category.UI_AND_DESIGN -> R.string.category_ui
        Category.DESIGNING_APP_ARCHITECTURE -> R.string.category_architecture
        Category.HARDWARE -> R.string.category_hadrdware
        Category.ANDROID_PLATFORMS -> R.string.category_platforms
        Category.MAINTENANCE_OPERATIONS_TESTING -> R.string.category_maintenance
        Category.DEVELOPMENT_PROCESSES -> R.string.category_development_processes
        Category.ANDROID_FRAMEWORK_AND_JETPACK -> R.string.category_framework
        Category.PRODUCTIVITY_AND_TOOLS -> R.string.category_productivity
        Category.CROSS_PLATFORM_DEVELOPMENT -> R.string.category_cross_platform
        Category.OTHER -> R.string.category_other
    }

@get:StringRes
val Room.text: Int
    get() = when (this) {
        Room.HALL_A -> R.string.room_hall_a
        Room.HALL_B -> R.string.room_hall_b
        Room.ROOM_1 -> R.string.room_1
        Room.ROOM_2 -> R.string.room_2
        Room.ROOM_3 -> R.string.room_3
        Room.ROOM_4 -> R.string.room_4
        Room.ROOM_5 -> R.string.room_5
        Room.ROOM_6 -> R.string.room_6
        Room.ROOM_7 -> R.string.room_7
    }

val TimeAndDate.text: String
    get() = "${date.monthValue}/${date.dayOfMonth} $startTime - $endTime"
