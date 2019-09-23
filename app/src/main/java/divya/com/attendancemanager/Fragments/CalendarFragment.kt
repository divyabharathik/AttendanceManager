package divya.com.attendancemanager.Fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.util.CollectionUtils
import divya.com.attendancemanager.Activities.EditAttendanceActivity
import divya.com.attendancemanager.Adapters.DailyDataAdapter
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.*
import kotlinx.android.synthetic.main.fragment_calender.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*


class CalendarFragment : Fragment(), DailyDataAdapter.OnItemClickListener {
    override fun onItemClick(dailyEntity: DailyEntity) {
//        dailyViewModel.update_daily(dailyEntity)
    }

    private lateinit var dailyViewModel: DailyViewModel
    private lateinit var recyclerViewAdapter: DailyDataAdapter
    private lateinit var detailRecyclerView : RecyclerView
    private lateinit var subjectsViewModel: SubjectsViewModel

    var date =""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calender, container, false) as ViewGroup
        val calendarView: CalendarView = rootView.findViewById(R.id.calender_view)
        detailRecyclerView = rootView.findViewById(R.id.date_detailRecyclerView)
        val headingText:TextView = rootView.findViewById(R.id.heading_text)
        val editImage:ImageView = rootView.findViewById(R.id.edit_img)
        val calendar: Calendar = getInstance()
        val df = SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault())
        date = df.format(calendar.time)
        headingText.text = "Attendance on $date"

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
        recyclerViewAdapter = DailyDataAdapter(arrayListOf(), this, "calendar",subjectArrayList)
        detailRecyclerView.adapter = recyclerViewAdapter
        detailRecyclerView.layoutManager = LinearLayoutManager(context)
        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        dailyViewModel.getDateSpecificList(date).observe(this, androidx.lifecycle.Observer { dailyData ->
            recyclerViewAdapter.addDailyDataAdapter(dailyData!!)
        })
        editImage.setOnClickListener {
            val intent = Intent(context, EditAttendanceActivity::class.java)
            intent.putExtra("date",date)
            startActivity(intent)
        }

        try {
            val beginDate = PreferenceConnector.readString(context, PreferenceConnector.BEGIN, "")
            val endDate = PreferenceConnector.readString(context, PreferenceConnector.END, "")
            val begin = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(beginDate)
            val end = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(endDate)
            calendarView.minDate = begin.time
            calendarView.maxDate = end.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(YEAR, year)
            calendar.set(MONTH, month)
            calendar.set(DAY_OF_MONTH, dayOfMonth)
            date = df.format(calendar.time)
            headingText.text = "Attendance on $date"
            recyclerViewAdapter = DailyDataAdapter(arrayListOf(), this, "calendar",subjectArrayList)
            detailRecyclerView.adapter = recyclerViewAdapter
            detailRecyclerView.layoutManager = LinearLayoutManager(context)
            dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
            dailyViewModel.getDateSpecificList(date).observe(this, androidx.lifecycle.Observer { dailyData ->
                recyclerViewAdapter.addDailyDataAdapter(dailyData!!)
            })
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
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
        recyclerViewAdapter = DailyDataAdapter(arrayListOf(), this, "calendar",subjectArrayList)
        detailRecyclerView.adapter = recyclerViewAdapter
        detailRecyclerView.layoutManager = LinearLayoutManager(context)
        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        dailyViewModel.getDateSpecificList(date).observe(this, androidx.lifecycle.Observer { dailyData ->
            recyclerViewAdapter.addDailyDataAdapter(dailyData!!)
        })
    }
}
