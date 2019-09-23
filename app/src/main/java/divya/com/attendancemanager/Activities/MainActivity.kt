package divya.com.attendancemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import divya.com.attendancemanager.Activities.BasicInfoActivity
import divya.com.attendancemanager.Activities.HomeActivity
import divya.com.attendancemanager.Activities.SubjectsActivity
import divya.com.attendancemanager.RoomData.PreferenceConnector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user =  PreferenceConnector.readString(this, PreferenceConnector.timetableEntered,"")
        get_started.setOnClickListener {
            val intent = Intent(this@MainActivity, BasicInfoActivity::class.java)
            startActivity(intent)
        }
        if(user != ""){
            // old user //
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
