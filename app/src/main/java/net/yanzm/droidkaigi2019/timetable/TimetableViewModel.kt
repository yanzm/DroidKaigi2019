package net.yanzm.droidkaigi2019.timetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yanzm.droidkaigi2019.domain.ConferenceDay
import net.yanzm.droidkaigi2019.domain.SessionRepository
import net.yanzm.droidkaigi2019.domain.TimetableItem
import kotlin.coroutines.CoroutineContext

class TimetableViewModel(
    private val day: ConferenceDay,
    private val repository: SessionRepository
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job = Job()

    private val _list = MutableLiveData<List<TimetableItem>>()
    val list: LiveData<List<TimetableItem>>
        get() = _list

    init {
        launch {
            _list.value = withContext(Dispatchers.Default) {
                repository.day(day)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    class Factory(
        private val day: ConferenceDay,
        private val sessionRepository: SessionRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TimetableViewModel(day, sessionRepository) as T
        }
    }
}
