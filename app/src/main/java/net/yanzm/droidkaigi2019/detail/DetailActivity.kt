package net.yanzm.droidkaigi2019.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yanzm.droidkaigi2019.R
import net.yanzm.droidkaigi2019.domain.SessionId
import net.yanzm.droidkaigi2019.sessionRepository
import net.yanzm.droidkaigi2019.text
import kotlin.coroutines.CoroutineContext

class DetailActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        job = Job()

        val sessionId = intent.getStringExtra(EXTRA_SESSION_ID)
            ?.let { SessionId(it) }
            ?: run {
                finish()
                return
            }

        launch {
            val session = withContext(Dispatchers.Default) {
                sessionRepository.sessionId(sessionId)
            }

            titleView.text = session.title
            abstractView.text = session.abstract
            speakerView.text = session.speaker.joinToString { it.name }
            sessionFormatView.setText(session.sessionFormat.text)
            languageView.setText(session.language.text)
            categoryView.setText(session.category.text)
            simultaneousInterpretationTargetView.visibility =
                if (session.simultaneousInterpretationTarget) View.VISIBLE else View.GONE
            roomView.setText(session.room.text)
            timeAndDateView.text = session.timeAndDate.text
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    companion object {
        private const val EXTRA_SESSION_ID = "session_id"

        fun createIntent(context: Context, sessionId: SessionId): Intent {
            return Intent(context, DetailActivity::class.java)
                .putExtra(EXTRA_SESSION_ID, sessionId.value)
        }
    }
}
