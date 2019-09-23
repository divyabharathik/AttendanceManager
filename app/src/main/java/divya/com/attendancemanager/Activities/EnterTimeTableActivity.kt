package divya.com.attendancemanager.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import divya.com.attendancemanager.Fragments.WeekdaysFragment
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class EnterTimeTableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_timetable)

        val timeTableViewModel = ViewModelProviders.of(this).get(TimeTableViewModel::class.java)
        val dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)


        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        setupViewPager(viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        val save = findViewById<ImageView>(R.id.save_img)
        save.setOnClickListener {
                        PreferenceConnector.writeString(
                this@EnterTimeTableActivity,
                PreferenceConnector.timetableEntered,
                "true"
            )

            val beginDate = PreferenceConnector.readString(
                this@EnterTimeTableActivity,
                PreferenceConnector.BEGIN,
                ""
            )
            val endDate = PreferenceConnector.readString(
                this@EnterTimeTableActivity,
                PreferenceConnector.END,
                ""
            )
            try {
                val begin = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(beginDate)
                val end = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(endDate)
                getDate(begin, end, timeTableViewModel, dailyViewModel)

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val intent = Intent(this@EnterTimeTableActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(WeekdaysFragment(), "Monday")
        adapter.addFragment(WeekdaysFragment(), "Tuesday")
        adapter.addFragment(WeekdaysFragment(), "Wednesday")
        adapter.addFragment(WeekdaysFragment(), "Thursday")
        adapter.addFragment(WeekdaysFragment(), "Friday")
        adapter.addFragment(WeekdaysFragment(), "Saturday")
        adapter.addFragment(WeekdaysFragment(), "Sunday")
        viewPager.adapter = adapter
    }

    private fun getDate(
        startDate: Date,
        endDate: Date,
        timeTableViewModel: TimeTableViewModel,
        dailyViewModel: DailyViewModel
    ) {
        val calendar = GregorianCalendar()
        calendar.time = startDate
        val endCalendar = GregorianCalendar()
        endCalendar.time = endDate
        println("--------------Enter time table activity--------------")
        while (calendar.before(endCalendar) || calendar==endCalendar) {
            val result = calendar.time
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val formattedDate = df.format(result)
            var day = ""
            println(formattedDate)
            when (dayOfWeek) {
                Calendar.MONDAY -> day = "Monday"
                Calendar.TUESDAY -> day = "Tuesday"
                Calendar.WEDNESDAY -> day = "Wednesday"
                Calendar.THURSDAY -> day = "Thursday"
                Calendar.FRIDAY -> day = "Friday"
                Calendar.SATURDAY -> day = "Saturday"
            }
            val listTimetable: List<TimetableEntity> = timeTableViewModel.getList(day)
            for (list in listTimetable) {
                val daily =
                    DailyEntity(
                        list.day,
                        formattedDate,
                        list.subject_name,
                        list.from,
                        list.to,
                        "0"
                    )
                dailyViewModel.addDailyData(daily)
            }

            calendar.add(Calendar.DATE, 1)
        }
        println("------------------------------------------")
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
            val args = Bundle()
            args.putString("Title", title)
            fragment.arguments = args
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}
