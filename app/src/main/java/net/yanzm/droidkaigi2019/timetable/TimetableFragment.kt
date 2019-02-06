package net.yanzm.droidkaigi2019.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_time_table.*
import net.yanzm.droidkaigi2019.R
import net.yanzm.droidkaigi2019.detail.DetailActivity
import net.yanzm.droidkaigi2019.domain.ConferenceDay
import net.yanzm.droidkaigi2019.domain.Session
import net.yanzm.droidkaigi2019.sessionRepository

class TimetableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_time_table, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val day = arguments!!.getSerializable(ARGS_DAY) as ConferenceDay

        val viewModel = ViewModelProviders
            .of(this, TimetableViewModel.Factory(day, requireContext().sessionRepository))
            .get(TimetableViewModel::class.java)

        fun createTimetableItemView(session: Session): TimetableItemView {
            val view = TimetableItemView(requireContext(), session)
            view.setOnClickListener {
                startActivity(DetailActivity.createIntent(requireContext(), session.id))
            }
            return view
        }

        viewModel.list.observe(this, Observer {
            it.forEach { session ->
                timetableView.addView(createTimetableItemView(session))
            }
        })
    }

    companion object {
        private const val ARGS_DAY = "day"

        fun newInstance(day: ConferenceDay): TimetableFragment {
            return TimetableFragment().apply {
                arguments = bundleOf(ARGS_DAY to day)
            }
        }
    }
}
