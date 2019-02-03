package net.yanzm.droidkaigi2019.domain

interface SessionRepository {

    fun day(day: ConferenceDay): List<Session>

    fun sessionId(id: SessionId): Session
}

enum class ConferenceDay {
    DAY1,
    DAY2
}
