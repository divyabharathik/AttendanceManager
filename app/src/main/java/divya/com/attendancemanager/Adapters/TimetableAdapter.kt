package divya.com.attendancemanager.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.TimetableEntity

class TimetableAdapter(listOfTimetable: ArrayList<TimetableEntity>, listener: OnItemClickListener) :
    RecyclerView.Adapter<TimetableAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.timetable_item, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val timetableEntity: TimetableEntity = listTimetable[position]
        val subjectname = timetableEntity.subject_name
        val fromtime = timetableEntity.from
        val totime = timetableEntity.to

        holder.subjectName.text = subjectname
        holder.fromTime.text = fromtime
        holder.toTime.text = totime
        holder.bind(timetableEntity, listenerContact, position)
    }

    private var listTimetable: List<TimetableEntity> = listOfTimetable

    private var listenerContact: OnItemClickListener = listener

    interface OnItemClickListener {
        fun onItemClick(timetableEntity: TimetableEntity)
    }

    override fun getItemCount(): Int {
        return listTimetable.size
    }

    fun addTimetable(listOfTimetable: List<TimetableEntity>) {
        this.listTimetable = listOfTimetable
        notifyDataSetChanged()
    }


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var clickedPosition: Int = 0
        var clicked: Boolean = false

        var subjectName = itemView.findViewById<TextView>(R.id.subject_name)!!
        var fromTime = itemView.findViewById<TextView>(R.id.from_time)!!
        var toTime = itemView.findViewById<TextView>(R.id.to_time)!!
        var cardView = itemView.findViewById<CardView>(R.id.timetable_card)!!

        fun bind(timetableEntity: TimetableEntity, listener: OnItemClickListener, position: Int) {

            itemView.setOnClickListener {

            }
        }

    }
}
