package net.yanzm.droidkaigi2019.session

import android.app.Application
import androidx.annotation.WorkerThread
import com.squareup.moshi.Moshi
import kotlinx.coroutines.yield
import net.yanzm.droidkaigi2019.domain.Category
import net.yanzm.droidkaigi2019.domain.Codelabs
import net.yanzm.droidkaigi2019.domain.ConferenceDay
import net.yanzm.droidkaigi2019.domain.FiresideChat
import net.yanzm.droidkaigi2019.domain.Language
import net.yanzm.droidkaigi2019.domain.Lunch
import net.yanzm.droidkaigi2019.domain.Party
import net.yanzm.droidkaigi2019.domain.PublicSession
import net.yanzm.droidkaigi2019.domain.Room
import net.yanzm.droidkaigi2019.domain.Session
import net.yanzm.droidkaigi2019.domain.SessionFormat
import net.yanzm.droidkaigi2019.domain.SessionId
import net.yanzm.droidkaigi2019.domain.SessionRepository
import net.yanzm.droidkaigi2019.domain.Speaker
import net.yanzm.droidkaigi2019.domain.SpeakerId
import net.yanzm.droidkaigi2019.domain.TimeAndDate
import net.yanzm.droidkaigi2019.domain.TimetableItem
import net.yanzm.droidkaigi2019.domain.WelcomeTalk
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class AssetsSessionRepository(private val application: Application) : SessionRepository {

    private val sessions: List<TimetableItem> by lazy {

        val json = application.assets.open("timetable.json").use {
            it.bufferedReader().readText()
        }

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(TimetableJson::class.java)
        val timetableJson = adapter.fromJson(json)!!

        val speakers = timetableJson.speakers
            .map {
                Speaker(
                    SpeakerId(it.id),
                    it.fullName,
                    it.tagLine ?: "",
                    it.bio ?: "",
                    it.profilePicture ?: ""
                )
            }

        timetableJson.sessions
            .mapNotNull {
                val s = LocalDateTime.parse(it.startsAt)
                val e = LocalDateTime.parse(it.endsAt)

                if (s.toLocalDate() != e.toLocalDate()) {
                    throw IllegalStateException()
                }

                val timeAndDate = TimeAndDate(
                    s.toLocalDate(),
                    s.toLocalTime(),
                    e.toLocalTime()
                )

                val room = when (it.roomId) {
                    3869 -> Room.HALL_A
                    3870 -> Room.HALL_B
                    3871 -> Room.ROOM_1
                    3872 -> Room.ROOM_2
                    3873 -> Room.ROOM_3
                    3874 -> Room.ROOM_4
                    3959 -> Room.ROOM_5
                    3875 -> Room.ROOM_6
                    3876 -> Room.ROOM_7
                    else -> throw IllegalStateException()
                }

                when (it.sessionType) {
                    "lunch" -> Lunch(timeAndDate)
                    "after_party" -> Party(room, timeAndDate)
                    "welcome_talk" -> {
                        WelcomeTalk(
                            SessionId(it.id),
                            it.title,
                            it.description,
                            room,
                            timeAndDate
                        )
                    }
                    "codelabs" -> {
                        Codelabs(
                            SessionId(it.id),
                            it.title,
                            it.description,
                            room,
                            timeAndDate
                        )
                    }
                    "fireside_chat" -> {
                        FiresideChat(
                            SessionId(it.id),
                            it.title,
                            it.description,
                            room,
                            timeAndDate
                        )
                    }
                    "normal" -> {
                        PublicSession(
                            SessionId(it.id),
                            it.title,
                            it.description,
                            it.speakers.map { speakerId ->
                                speakers.first { speaker ->
                                    speakerId == speaker.id.value
                                }
                            },
                            when {
                                it.categoryItems.contains(13199) -> SessionFormat.MIN_30
                                it.categoryItems.contains(13200) -> SessionFormat.MIN_50
                                else -> throw IllegalStateException()
                            },
                            when {
                                it.categoryItems.contains(13201) -> Language.EN
                                it.categoryItems.contains(13202) -> Language.JA
                                it.categoryItems.contains(13203) -> Language.MIX
                                else -> throw IllegalStateException()
                            },
                            when {
                                it.categoryItems.contains(13557) -> Category.XR
                                it.categoryItems.contains(13558) -> Category.SECURITY
                                it.categoryItems.contains(13559) -> Category.UI_AND_DESIGN
                                it.categoryItems.contains(13560) -> Category.DESIGNING_APP_ARCHITECTURE
                                it.categoryItems.contains(13561) -> Category.HARDWARE
                                it.categoryItems.contains(13562) -> Category.ANDROID_PLATFORMS
                                it.categoryItems.contains(13598) -> Category.MAINTENANCE_OPERATIONS_TESTING
                                it.categoryItems.contains(13566) -> Category.DEVELOPMENT_PROCESSES
                                it.categoryItems.contains(13567) -> Category.ANDROID_FRAMEWORK_AND_JETPACK
                                it.categoryItems.contains(13568) -> Category.PRODUCTIVITY_AND_TOOLS
                                it.categoryItems.contains(13569) -> Category.CROSS_PLATFORM_DEVELOPMENT
                                it.categoryItems.contains(13570) -> Category.OTHER
                                else -> throw IllegalStateException()
                            },
                            it.interpretationTarget,
                            room,
                            timeAndDate
                        )
                    }
                    else -> null
                }
            }
    }

    @WorkerThread
    override suspend fun day(day: ConferenceDay): List<TimetableItem> {
        val date = when (day) {
            ConferenceDay.DAY1 -> LocalDate.of(2019, 2, 7)
            ConferenceDay.DAY2 -> LocalDate.of(2019, 2, 8)
        }
        val sessions = sessions
        yield()
        return sessions.filter { it.timeAndDate.date == date }
    }

    @WorkerThread
    override suspend fun sessionId(id: SessionId): Session {
        val sessions = sessions
        yield()
        return sessions.asSequence()
            .filterIsInstance(Session::class.java)
            .first { it.id == id }
    }
}

data class TimetableJson(
    val sessions: List<SessionJson>,
    val speakers: List<SpeakerJson>
)

data class SpeakerJson(
    val id: String,
    val fullName: String,
    val bio: String?,
    val tagLine: String?,
    val profilePicture: String?
)

data class SessionJson(
    val id: String,
    val title: String,
    val description: String,
    val roomId: Int,
    val startsAt: String,
    val endsAt: String,
    val categoryItems: List<Int>,
    val speakers: List<String>,
    val interpretationTarget: Boolean,
    val sessionType: String
)
