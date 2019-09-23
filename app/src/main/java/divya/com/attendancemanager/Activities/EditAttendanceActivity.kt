package divya.com.attendancemanager.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.util.CollectionUtils
import divya.com.attendancemanager.Adapters.DailyDataAdapter
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.*
import kotlinx.android.synthetic.main.activity_edit_attendance.*
import java.util.ArrayList

class EditAttendanceActivity : AppCompatActivity() , DailyDataAdapter.OnItemClickListener {
    override fun onItemClick(dailyEntity: DailyEntity) {
        dailyViewModel.update_daily(dailyEntity)
    }
    private var timeTableViewModel: TimeTableViewModel? = null
    private lateinit var dailyViewModel: DailyViewModel
    private var recyclerViewAdapter: DailyDataAdapter? = null
    private lateinit var subjectsViewModel: SubjectsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_attendance)
        val date: String = intent.getStringExtra("date")
        date_textview.text = "Date: $date"
        editDailyRecyclerView!!.layoutManager = LinearLayoutManager(this)
        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        subjectsViewModel = ViewModelProviders.of(this).get(SubjectsViewModel::class.java)
        val arrayList = ArrayList<String>()
        val subjectArrayList = ArrayList<ProfileSubjectEntity>()
        arrayList.addAll(CollectionUtils.listOf(*subjectsViewModel.list))
        for (i in arrayList){
            val present = dailyViewModel.getCount(i,"present")
            val absent = dailyViewModel.getCount(i,"absent")
            val cancel = dailyViewModel.getCount(i,"cancel")
            val total = present+absent
            var percentage: Float
            if (total == 0){
                percentage =0f
            }else {
                percentage = present  * 100f/ total
            }
            subjectArrayList.add(ProfileSubjectEntity(i,present, absent, cancel,percentage,total))
        }

        recyclerViewAdapter = DailyDataAdapter(arrayListOf(), this, "edit",subjectArrayList)
        editDailyRecyclerView!!.adapter = recyclerViewAdapter
        timeTableViewModel = ViewModelProviders.of(this).get(TimeTableViewModel::class.java)
        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        dailyViewModel.getDateSpecificList(date).observe(this, androidx.lifecycle.Observer { dailyData ->
                recyclerViewAdapter!!.addDailyDataAdapter(dailyData!!)
            })
        back_img.setOnClickListener {
            onBackPressed()
        }
    }
}
