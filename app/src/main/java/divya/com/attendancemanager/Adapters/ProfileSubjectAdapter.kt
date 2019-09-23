package divya.com.attendancemanager.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.PreferenceConnector
import divya.com.attendancemanager.RoomData.ProfileSubjectEntity
import java.text.DecimalFormat

class ProfileSubjectAdapter(
    listOfTimetable: ArrayList<ProfileSubjectEntity>,
    private var context: Context
) :
    RecyclerView.Adapter<ProfileSubjectAdapter.RecyclerViewHolder>() {
    private var listTimetable: List<ProfileSubjectEntity> = listOfTimetable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.profile_subject_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val profileSubjectEntity: ProfileSubjectEntity = listTimetable[position]
        holder.subjectName.text = profileSubjectEntity.subjectName
        holder.presentTextView.text = profileSubjectEntity.present.toString()
        holder.absentTextView.text = profileSubjectEntity.absent.toString()
        holder.cancelTextView.text = profileSubjectEntity.cancel.toString()
        holder.totalTextView.text = "Total: "+profileSubjectEntity.total
        holder.percentageTextView.text = DecimalFormat("#.##").format(profileSubjectEntity.percentage)
        val criteria = PreferenceConnector.readString(context, PreferenceConnector.Criteria, "75").toFloat()
        var count = 0
        var percentage : Float = profileSubjectEntity.percentage
        while (profileSubjectEntity.total != 0 && percentage < criteria) {
            count++
            percentage = (profileSubjectEntity.present + count) * 100f / (profileSubjectEntity.total+count)
            holder.compensationTextView.text = "Attend next $count classes"
        }
        if(count==0) holder.compensationTextView.text = "Perfect attendance!!!"

    }

    override fun getItemCount(): Int {
        return listTimetable.size
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var subjectName = itemView.findViewById<TextView>(R.id.subject_tv)!!
        var compensationTextView = itemView.findViewById<TextView>(R.id.compensation_tv)!!
        var presentTextView = itemView.findViewById<TextView>(R.id.present_tv)!!
        var absentTextView = itemView.findViewById<TextView>(R.id.absent_tv)!!
        var cancelTextView = itemView.findViewById<TextView>(R.id.cancel_tv)!!
        var totalTextView = itemView.findViewById<TextView>(R.id.total_tv)!!
        var percentageTextView = itemView.findViewById<TextView>(R.id.percentage_tv)!!
    }
}
