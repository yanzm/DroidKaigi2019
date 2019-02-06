package net.yanzm.droidkaigi2019.timetable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_timetable.*
import net.yanzm.droidkaigi2019.R
import net.yanzm.droidkaigi2019.domain.ConferenceDay

class TimetableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)

        pager.adapter = Adapter(supportFragmentManager)
        tabLayout.setupWithViewPager(pager)
    }

    private class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> TimetableFragment.newInstance(ConferenceDay.DAY1)
                1 -> TimetableFragment.newInstance(ConferenceDay.DAY2)
                else -> throw IllegalStateException()
            }
        }

        override fun getCount() = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "DAY 1"
                1 -> "DAY 2"
                else -> throw IllegalStateException()
            }
        }
    }
}
