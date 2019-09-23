package divya.com.attendancemanager.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import divya.com.attendancemanager.R
import java.util.ArrayList

class TimetableFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_timetable, container, false)
        val viewPager = rootView.findViewById<ViewPager>(R.id.viewpager)
        setupViewPager(viewPager)
        val tabLayout = rootView.findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        return rootView
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(fragmentManager!!)
        adapter.addFragment(WeekdaysFragment(), "Monday","no_change")
        adapter.addFragment(WeekdaysFragment(), "Tuesday","no_change")
        adapter.addFragment(WeekdaysFragment(), "Wednesday","no_change")
        adapter.addFragment(WeekdaysFragment(), "Thursday","no_change")
        adapter.addFragment(WeekdaysFragment(), "Friday","no_change")
        adapter.addFragment(WeekdaysFragment(), "Saturday","no_change")
        adapter.addFragment(WeekdaysFragment(), "Sunday","no_change")
        viewPager.adapter = adapter
    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String,any_changes: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
            val args = Bundle()
            args.putString("Title", title)
            args.putString("any",any_changes)
            fragment.arguments = args
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

}
