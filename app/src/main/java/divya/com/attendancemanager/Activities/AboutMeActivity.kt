package divya.com.attendancemanager.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import divya.com.attendancemanager.R
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)
        close_img.setOnClickListener { onBackPressed() }
    }
}
