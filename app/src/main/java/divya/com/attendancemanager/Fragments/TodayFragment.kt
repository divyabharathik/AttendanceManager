package divya.com.attendancemanager.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import divya.com.attendancemanager.Adapters.DailyDataAdapter
import divya.com.attendancemanager.RoomData.*
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.ColorMatrixColorFilter
import android.graphics.ColorMatrix
import com.google.android.gms.common.util.CollectionUtils
import divya.com.attendancemanager.Activities.AboutMeActivity
import divya.com.attendancemanager.Activities.EnterTimeTableActivity
import divya.com.attendancemanager.Activities.SubjectsActivity
import divya.com.attendancemanager.R
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.setting_dialog.*
import java.text.DecimalFormat


class TodayFragment : Fragment(), DailyDataAdapter.OnItemClickListener {
    override fun onItemClick(dailyEntity: DailyEntity) {
        dailyViewModel.update_daily(dailyEntity)
    }
    private lateinit var subjectsViewModel: SubjectsViewModel
    private var timeTableViewModel: TimeTableViewModel? = null
    private lateinit var dailyViewModel: DailyViewModel
    private var recyclerView: RecyclerView? = null
    private var timetableDao: TimetableDao? = null
    private var recyclerViewAdapter: DailyDataAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_today, container, false) as ViewGroup
        recyclerView = rootView.findViewById(R.id.dailyRecyclerView)
        val menu = rootView.findViewById<ImageView>(R.id.menu_img)
        val name_of_user = rootView.findViewById<TextView>(R.id.name_of_user)
        val attendance_percentage_tv = rootView.findViewById<TextView>(R.id.percentage_tv)
        val appDatabase: AppDatabase = AppDatabase.getDataBase(context!!)
        timetableDao = appDatabase.timetableDao()

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
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = DailyDataAdapter(arrayListOf(), this, "today",subjectArrayList)
        recyclerView!!.adapter = recyclerViewAdapter
        var totalPercentage = 0f
        for(i in subjectArrayList){
            totalPercentage += i.percentage
        }
        totalPercentage /= subjectArrayList.size

        name_of_user.text = PreferenceConnector.readString(context,PreferenceConnector.Name,"")
        attendance_percentage_tv.text = DecimalFormat("#.##").format(totalPercentage)+" %"
        timeTableViewModel = ViewModelProviders.of(this).get(TimeTableViewModel::class.java)
        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)

        menu.setOnClickListener {
            openBottomNavigationMenu()
        }

        val calendar: Calendar = Calendar.getInstance()
//        calendar.add(Calendar.DATE,1)
        val result = calendar.time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val formattedDate = df.format(result)
        dailyViewModel.getDateSpecificList(formattedDate)
            .observe(this, androidx.lifecycle.Observer { dailyData ->
                recyclerViewAdapter!!.addDailyDataAdapter(dailyData!!)
            })
        return rootView
    }

    private fun openBottomNavigationMenu() {
        val customLay = layoutInflater.inflate(R.layout.setting_dialog, null)
        val dialog = BottomSheetDialog(context!!)
        dialog.setContentView(customLay)
        val imageViewsubject = customLay.findViewById<ImageView>(R.id.imageViewsubject)
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        val filter = ColorMatrixColorFilter(matrix)
        imageViewsubject.colorFilter = filter
        val about_tv = customLay.findViewById<TextView>(R.id.about_tv)
        val notifications_tv = customLay.findViewById<TextView>(R.id.notifications_tv)
        val subjects_tv = customLay.findViewById<TextView>(R.id.subjects_tv)
        val timetable_tv = customLay.findViewById<TextView>(R.id.timetable_tv)
        about_tv.setOnClickListener {
            startActivity(Intent(context, AboutMeActivity::class.java))
        }
        notifications_tv.setOnClickListener {
            startActivity(Intent(context, AboutMeActivity::class.java))
        }
        subjects_tv.setOnClickListener {
            startActivity(Intent(context, SubjectsActivity::class.java))
        }
        timetable_tv.setOnClickListener {
            startActivity(Intent(context, EnterTimeTableActivity::class.java))
        }

        dialog.show()
    }
}
