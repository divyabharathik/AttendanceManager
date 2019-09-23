package divya.com.attendancemanager.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import divya.com.attendancemanager.Adapters.SubjectsAdapter
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.AppDatabase
import divya.com.attendancemanager.RoomData.SubjectDao
import divya.com.attendancemanager.RoomData.SubjectEntity
import divya.com.attendancemanager.RoomData.SubjectsViewModel
import kotlinx.android.synthetic.main.activity_subjects.*


class SubjectsActivity : AppCompatActivity(), SubjectsAdapter.OnItemClickListener {
    override fun onItemClick(subject: SubjectEntity) {
        remove_btn.isEnabled = true
        remove_btn.setBackgroundColor(resources.getColor(R.color.colorAccent))
        remove_btn.setOnClickListener { subjectsViewModel!!.removeSubject(subject) }
    }

    private lateinit var subjectsViewModel: SubjectsViewModel
    private lateinit var recyclerViewAdapter: SubjectsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)
        textinput_edit_subject.requestFocus()
        recyclerViewAdapter = SubjectsAdapter(arrayListOf(), this)
        subjectRecyclerView!!.layoutManager = LinearLayoutManager(this)
        subjectRecyclerView!!.adapter = recyclerViewAdapter
        subjectsViewModel = ViewModelProviders.of(this).get(SubjectsViewModel::class.java)

        subjectsViewModel.listSubject.observe(this, Observer { subjects ->
            recyclerViewAdapter.addSubjects(subjects!!)
        })

        add_btn.setOnClickListener { addSubject() }
        remove_btn.isEnabled = false

        enter_timetable_btn.setOnClickListener {
            val intent = Intent(this@SubjectsActivity, EnterTimeTableActivity::class.java)
            startActivity(intent)
        }

    }

    private fun addSubject() {
        val subjectName = textinput_edit_subject.text.toString()
        if (subjectName != "") {
            textinput_edit_subject.setText("")
            val subjectEntity = SubjectEntity(subjectName)
            subjectsViewModel!!.addSubject(subjectEntity)
        }
    }

}
