package divya.com.attendancemanager.Activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import divya.com.attendancemanager.Fragments.CalendarFragment
import divya.com.attendancemanager.Fragments.ProfileFragment
import divya.com.attendancemanager.Fragments.TimetableFragment
import divya.com.attendancemanager.Fragments.TodayFragment
import divya.com.attendancemanager.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        loadFragment(TodayFragment())
        bottom_navigation.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        bottom_navigation.selectedItemId = R.id.bottom_nav_today
    }

    private val onBottomNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val fragment: Fragment
                when (item.itemId) {
                    R.id.bottom_nav_today -> {
                        fragment = TodayFragment()
                        loadFragment(fragment)
                        return true
                    }
                    R.id.bottom_nav_timetable -> {
                        fragment = TimetableFragment()
                        loadFragment(fragment)
                        return true
                    }
                    R.id.bottom_nav_calender -> {
                        fragment = CalendarFragment()
                        loadFragment(fragment)
                        return true
                    }
                    R.id.bottom_nav_profile -> {
                        fragment = ProfileFragment()
                        loadFragment(fragment)
                        return true
                    }
                }
                return false
            }
        }


    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
