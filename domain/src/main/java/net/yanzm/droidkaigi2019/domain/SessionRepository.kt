package net.yanzm.droidkaigi2019.domain

interface SessionRepository {

    suspend fun day(day: ConferenceDay): List<TimetableItem>

    suspend fun sessionId(id: SessionId): Session
}

enum class ConferenceDay {
    DAY1,
    DAY2
}
