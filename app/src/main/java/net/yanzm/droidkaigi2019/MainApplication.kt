package net.yanzm.droidkaigi2019

import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import net.yanzm.droidkaigi2019.domain.SessionRepository
import net.yanzm.droidkaigi2019.session.AssetsSessionRepository

class MainApplication : Application() {

    var sessionRepository: SessionRepository = AssetsSessionRepository(this)
        private set

    @VisibleForTesting
    fun replaceSessionRepository(sessionRepository: SessionRepository) {
        this.sessionRepository = sessionRepository
    }
}

val Context.sessionRepository: SessionRepository
    get() = (applicationContext as MainApplication).sessionRepository
