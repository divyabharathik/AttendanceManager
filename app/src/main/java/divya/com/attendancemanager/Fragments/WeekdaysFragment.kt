package divya.com.attendancemanager.Fragments

import android.app.TimePickerDialog
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
import com.google.android.material.button.MaterialButton
import divya.com.attendancemanager.Adapters.CustomSpinnerAdapter
import divya.com.attendancemanager.Adapters.TimetableAdapter
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.*
import java.text.SimpleDateFormat
import java.util.*


class WeekdaysFragment : Fragment(), TimetableAdapter.OnItemClickListener {
    override fun onItemClick(timetableEntity: TimetableEntity) {

    }

    private var subjectsViewModel: SubjectsViewModel? = null
    private var timeTableViewModel: TimeTableViewModel? = null
    private var recyclerView: RecyclerView? = null
    private var timetableDao: TimetableDao? = null
    private var recyclerViewAdapter: TimetableAdapter? = null
    private var title = ""
    private var changes = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments!!.getString("Title", "")
        changes = arguments!!.getString("any", "")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_weekdays, container, false) as ViewGroup
        val addSubjectBtn = rootView.findViewById<MaterialButton>(R.id.add_sub_to_tt)
        recyclerView = rootView.findViewById(R.id.timetableRecyclerView)

        if(changes == "no_change"){
            addSubjectBtn.visibility = View.GONE
        }
        val appDatabase: AppDatabase = AppDatabase.getDataBase(context!!)
        timetableDao = appDatabase.timetableDao()
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = TimetableAdapter(arrayListOf(), this)
        recyclerView!!.adapter = recyclerViewAdapter
        subjectsViewModel = ViewModelProviders.of(this).get(SubjectsViewModel::class.java)
        timeTableViewModel = ViewModelProviders.of(this).get(TimeTableViewModel::class.java)
        if (title == "Monday" || title == "Tuesday" || title == "Wednesday" || title == "Thursday" || title == "Friday" || title == "Saturday") {
            timeTableViewModel!!.getLiveListTimetable(title).observe(this, androidx.lifecycle.Observer { timetables ->
                recyclerViewAdapter!!.addTimetable(timetables!!)
            })
        }
        addSubjectBtn.setOnClickListener { addSubject() }

        return rootView
    }

    private fun addSubject() {
        val customLay = layoutInflater.inflate(R.layout.add_subject_dialog, null)
        val close = customLay.findViewById<ImageView>(R.id.close_img)
        val spinner = customLay.findViewById<Spinner>(R.id.subjects_spinner)
        val day = customLay.findViewById<TextView>(R.id.day_tv)
        val fromTime = customLay.findViewById<TextView>(R.id.from_time_tv)
        val toTime = customLay.findViewById<TextView>(R.id.to_time_tv)
        val save = customLay.findViewById<MaterialButton>(R.id.save_mt_btn)

        day.text = title

        setTimeTextView(fromTime)
        setTimeTextView(toTime)
        val addSubjectDialog = AlertDialog.Builder(context!!)
        val alertDialog = addSubjectDialog.create()
        alertDialog.setView(customLay)
        alertDialog.show()
        val arrayList = ArrayList<String>()
        arrayList.add("Click here to select a subject")
        arrayList.addAll(listOf(*subjectsViewModel!!.getSubjectsAsList()))
        val customSpinnerAdapter = CustomSpinnerAdapter(context, arrayList)
        spinner.adapter = customSpinnerAdapter
        save.setOnClickListener {
            if (fromTime.background !== ContextCompat.getDrawable(this.requireContext(),R.drawable.unselected_border)
                && toTime.background !== ContextCompat.getDrawable(this.requireContext(),R.drawable.unselected_border)
                && arrayList[spinner.selectedItemPosition] != "Click here to select a subject") {
                val timetableEntity = TimetableEntity(title, arrayList[spinner.selectedItemPosition], fromTime.text.toString(), toTime.text.toString())
                timeTableViewModel!!.addTimetable(timetableEntity)
                println("time table added-------------------------------------------------------------------------------------")
                alertDialog.dismiss()
            }
        }

        close.setOnClickListener { alertDialog.dismiss() }
    }

    private fun setTimeTextView(fromTime: TextView) {
        val cc = Calendar.getInstance()
        val mHour = cc.get(Calendar.HOUR_OF_DAY)
        val minute = IntArray(1)
        val hour = intArrayOf(mHour)
        val new_DelivTime = arrayOf("")
        fromTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { timePicker, i, i1 ->
                hour[0] = i
                minute[0] = i1
                var Timeformat = ""
                if (hour[0] == 0) {
                    hour[0] += 12
                    Timeformat = "AM"
                } else if (hour[0] == 12) {
                    Timeformat = "PM"
                } else if (hour[0] > 12) {
                    hour[0] -= 12
                    Timeformat = "PM"
                } else {
                    Timeformat = "AM"
                }

                var min_str = minute[0].toString()
                if (min_str.length == 1) {
                    min_str = "0$min_str"
                }
                fromTime.text = StringBuilder().append(hour[0])
                    .append(":").append(min_str).append(" ")
                    .append(Timeformat)

                val displayFormat = SimpleDateFormat(
                    "HH:mm",
                    Locale.ENGLISH
                )
                val parseFormat = SimpleDateFormat(
                    "hh:mm a",
                    Locale.ENGLISH
                )

                try {
                    val date = parseFormat.parse(fromTime.text.toString())
                    println(
                        parseFormat.format(date) + " = "
                                + displayFormat.format(date)
                    )
                    new_DelivTime[0] = displayFormat.format(date)

                } catch (e: Exception) {
                    e.message
                }

                fromTime.background = resources.getDrawable(R.drawable.selected_border)
                println(" Selected Deliv time " + new_DelivTime[0])
            }, hour[0], minute[0], false)
            timePickerDialog.show()
        }
    }

}

