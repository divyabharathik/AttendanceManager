package divya.com.attendancemanager.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.util.CollectionUtils.listOf
import divya.com.attendancemanager.Adapters.ProfileSubjectAdapter
import divya.com.attendancemanager.Adapters.TimetableAdapter

import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.DailyViewModel
import divya.com.attendancemanager.RoomData.PreferenceConnector
import divya.com.attendancemanager.RoomData.ProfileSubjectEntity
import divya.com.attendancemanager.RoomData.SubjectsViewModel
import java.text.DecimalFormat
import java.util.ArrayList


class ProfileFragment : Fragment() {
    private lateinit var dailyViewModel: DailyViewModel
    private lateinit var subjectsViewModel: SubjectsViewModel
    private lateinit var recyclerViewAdapter: ProfileSubjectAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false) as ViewGroup
        val recyclerView : RecyclerView = rootView.findViewById(R.id.subject_profile_recycler_view)
        val userName:TextView = rootView.findViewById(R.id.username_tv)
        val subjects:TextView = rootView.findViewById(R.id.subject_no_tv)
        val attendancePercentage:TextView = rootView.findViewById(R.id.attendance_percentage_tv)

        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)
        subjectsViewModel = ViewModelProviders.of(this).get(SubjectsViewModel::class.java)
        val arrayList = ArrayList<String>()
        val subjectArrayList = ArrayList<ProfileSubjectEntity>()
        arrayList.addAll(listOf(*subjectsViewModel.list))
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
        var totalPercentage = 0f
        for(i in subjectArrayList){
            totalPercentage += i.percentage
        }
        totalPercentage /= subjectArrayList.size
        userName.text = PreferenceConnector.readString(context,PreferenceConnector.Name,"")
        subjects.text = subjectArrayList.size.toString()+" Subjects"
        attendancePercentage.text = DecimalFormat("#.##").format(totalPercentage)+" %"
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = ProfileSubjectAdapter(subjectArrayList,context!!)
        recyclerView.adapter = recyclerViewAdapter
        return rootView
    }

}
