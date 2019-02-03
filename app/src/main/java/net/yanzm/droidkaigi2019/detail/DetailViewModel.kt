package net.yanzm.droidkaigi2019.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yanzm.droidkaigi2019.domain.Session
import net.yanzm.droidkaigi2019.domain.SessionId
import net.yanzm.droidkaigi2019.domain.SessionRepository
import kotlin.coroutines.CoroutineContext

class DetailViewModel(
    private val id: SessionId,
    private val repository: SessionRepository
) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val _session = MutableLiveData<Session>()
    val session: LiveData<Session>
        get() = _session

    init {
        launch {
            val result = withContext(Dispatchers.Default) {
                repository.sessionId(id)
            }
            _session.value = result

        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    class Factory(
        private val id: SessionId,
        private val repository: SessionRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(id, repository) as T
        }
    }
}
