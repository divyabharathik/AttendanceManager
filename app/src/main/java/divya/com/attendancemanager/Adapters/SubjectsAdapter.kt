package divya.com.attendancemanager.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import divya.com.attendancemanager.R
import divya.com.attendancemanager.RoomData.SubjectEntity



class SubjectsAdapter(contacts: ArrayList<SubjectEntity>, listener: OnItemClickListener) :
    RecyclerView.Adapter<SubjectsAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent!!.context).inflate(divya.com.attendancemanager.R.layout.subject_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentContact: SubjectEntity = listSubjects[position]
        val nameContact = currentContact.subject_name
        holder.mName.text = nameContact
        holder.cardView.background.setTint(Color.WHITE)
        holder.bind(currentContact, listenerContact,position)
    }

    private var listSubjects: List<SubjectEntity> = contacts

    private var listenerContact: OnItemClickListener = listener

    interface OnItemClickListener {
        fun onItemClick(subjectEntity: SubjectEntity)
    }

    override fun getItemCount(): Int {
        return listSubjects.size
    }

    fun addSubjects(listContacts: List<SubjectEntity>) {
        this.listSubjects = listContacts
        notifyDataSetChanged()
    }


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var clickedPosition: Int = 0
        var clicked: Boolean = false

        var mName = itemView.findViewById<TextView>(R.id.subject_name_text)!!
        var cardView = itemView.findViewById<CardView>(R.id.subject_name_card)!!

        fun bind(subjectEntity: SubjectEntity, listener: OnItemClickListener,position: Int) {

            itemView.setOnClickListener{
                clickedPosition = position
                clicked = true
//                if(clicked){
//                    if(position == clickedPosition)
//
//                    else
//                        cardView.background.setTint(Color.WHITE)
//                }
                cardView.background.setTint(Color.parseColor("#E8FFDA6D"))
                listener.onItemClick(subjectEntity)
            }
        }

    }
}