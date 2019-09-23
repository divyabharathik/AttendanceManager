package divya.com.attendancemanager.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.DailyEntity
import java.util.*
import divya.com.attendancemanager.Utils.MyDiffCallback
import androidx.recyclerview.widget.DiffUtil
import divya.com.attendancemanager.RoomData.PreferenceConnector
import divya.com.attendancemanager.RoomData.ProfileSubjectEntity
import java.text.DecimalFormat


class DailyDataAdapter(listOfTimetable: ArrayList<DailyEntity>, listener: OnItemClickListener, from: String, subjectArrayList: ArrayList<ProfileSubjectEntity>) :
    RecyclerView.Adapter<DailyDataAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.daily_item, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val dailyEntity: DailyEntity = listTimetable[position]
        holder.subjectName.text = dailyEntity.subject_name
        holder.fromTime.text = dailyEntity.from
        holder.toTime.text = dailyEntity.to


        for(i in subjectArrayList){
            if(i.subjectName == dailyEntity.subject_name){
                holder.subjectPercentage.text = DecimalFormat("#.##").format(i.percentage)+" %"
                var count = 0
                var percentage : Float = i.percentage
                while (i.total != 0 && percentage < 75) {
                    count++
                    percentage = (i.present + count) * 100f / (i.total+count)
                    holder.compensation.text = "Attend next $count classes"
                }
                if(count==0) holder.compensation.text = "Perfect attendance!!!"
            }
        }

        when {
            dailyEntity.present_absent_cancel == "present" -> {
                holder.present.background.setTint(Color.parseColor("#00D469"))
                holder.presentTV.setTextColor(Color.WHITE)
            }
            dailyEntity.present_absent_cancel == "absent" -> {
                holder.absent.background.setTint(Color.parseColor("#F44336"))
                holder.absentTV.setTextColor(Color.WHITE)
            }
            dailyEntity.present_absent_cancel == "cancel" -> {
                holder.cancel.background.setTint(Color.parseColor("#FFC107"))
                holder.cancelTV.setTextColor(Color.WHITE)
            }
        }

        if(from!="calendar") {
            holder.present.setOnClickListener {
                dailyEntity.present_absent_cancel = "present"
                holder.present.background.setTint(Color.parseColor("#00D469"))
                holder.absent.background.setTint(Color.WHITE)
                holder.cancel.background.setTint(Color.WHITE)
                holder.presentTV.setTextColor(Color.WHITE)
                holder.absentTV.setTextColor(Color.parseColor("#702B2A2A"))
                holder.cancelTV.setTextColor(Color.parseColor("#702B2A2A"))
                listenerData.onItemClick(dailyEntity)
            }

            holder.absent.setOnClickListener {
                dailyEntity.present_absent_cancel = "absent"
                holder.absent.background.setTint(Color.parseColor("#F44336"))
                holder.present.background.setTint(Color.WHITE)
                holder.cancel.background.setTint(Color.WHITE)
                holder.absentTV.setTextColor(Color.WHITE)
                holder.presentTV.setTextColor(Color.parseColor("#702B2A2A"))
                holder.cancelTV.setTextColor(Color.parseColor("#702B2A2A"))
                listenerData.onItemClick(dailyEntity)
            }

            holder.cancel.setOnClickListener {
                dailyEntity.present_absent_cancel = "cancel"
                holder.cancel.background.setTint(Color.parseColor("#FFC107"))
                holder.present.background.setTint(Color.WHITE)
                holder.absent.background.setTint(Color.WHITE)
                holder.cancelTV.setTextColor(Color.WHITE)
                holder.presentTV.setTextColor(Color.parseColor("#702B2A2A"))
                holder.absentTV.setTextColor(Color.parseColor("#702B2A2A"))
                listenerData.onItemClick(dailyEntity)
            }
        }
    }

    private var listTimetable: List<DailyEntity> = listOfTimetable
    private var listenerData: OnItemClickListener = listener
    private var subjectArrayList: ArrayList<ProfileSubjectEntity> = subjectArrayList
    private var from: String = from

    interface OnItemClickListener {
        fun onItemClick(dailyEntity: DailyEntity)
    }

    override fun getItemCount(): Int {
        return listTimetable.size
    }

    fun addDailyDataAdapter(listOfDailyData: List<DailyEntity>) {
        this.listTimetable = listOfDailyData
        updateList(listOfDailyData)
        notifyDataSetChanged()
    }

    private fun updateList(newList: List<DailyEntity>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(this.listTimetable, newList))
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectName = itemView.findViewById<TextView>(R.id.subject_tv)!!
        var fromTime = itemView.findViewById<TextView>(R.id.from_time)!!
        var toTime = itemView.findViewById<TextView>(R.id.to_time)!!

        var compensation = itemView.findViewById<TextView>(R.id.compensation_tv)
        var subjectPercentage = itemView.findViewById<TextView>(R.id.subject_percentage_tv)

        var present = itemView.findViewById<CardView>(R.id.present_cons)!!
        var absent = itemView.findViewById<CardView>(R.id.absent_cons)!!
        var cancel = itemView.findViewById<CardView>(R.id.cancel_cons)!!

        var presentTV = itemView.findViewById<TextView>(R.id.present_tv)!!
        var absentTV = itemView.findViewById<TextView>(R.id.absent_tv)!!
        var cancelTV = itemView.findViewById<TextView>(R.id.cancel_tv)!!

    }
}
