package divya.com.attendancemanager.RoomData

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class SubjectsViewModel(application: Application) : AndroidViewModel(application) {

    var listSubject: LiveData<List<SubjectEntity>>
    var list: Array<String>
    private val appDb: AppDatabase = AppDatabase.getDataBase(this.getApplication())

    init {
        listSubject = appDb.subjectDao().getAllSubjects()
        list = appDb.subjectDao().getAllSubjectsAscending()
    }

    fun getSubjectsAsList(): Array<String>{
        return list
    }
//    private fun convert(liveList: LiveData<List<SubjectEntity>>): ArrayList<String> {
//        val arrayListString = ArrayList<String>()
//        println("==========================here==================================")
//        for(i in liveList.value!!){
//            arrayListString.add(i.subject_name)
//        }
//        return arrayListString
//    }

    fun addSubject(subjectEntity: SubjectEntity) {
        AddAsyncTask(appDb).execute(subjectEntity)
    }


    class AddAsyncTask(db: AppDatabase) : AsyncTask<SubjectEntity, Void, Void>() {
        private var subjectsDb = db
        override fun doInBackground(vararg params: SubjectEntity): Void? {
            subjectsDb.subjectDao().insert(params[0])
            return null
        }

    }

    fun removeSubject(subjectEntity: SubjectEntity) {
        RemoveAsyncTask(appDb).execute(subjectEntity)
    }


    class RemoveAsyncTask(db: AppDatabase) : AsyncTask<SubjectEntity, Void, Void>() {
        private var subjectsDb = db
        override fun doInBackground(vararg params: SubjectEntity): Void? {
            subjectsDb.subjectDao().deleteSubject(params[0])
            return null
        }

    }

}