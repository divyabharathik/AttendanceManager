package divya.com.attendancemanager.RoomData

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TimeTableViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var list: List<TimetableEntity>
    private lateinit var liveList: LiveData<List<TimetableEntity>>
    private val appDb: AppDatabase = AppDatabase.getDataBase(this.getApplication())

    fun getList(day: String): List<TimetableEntity> {
        list = appDb.timetableDao().getList(day)
        return list
    }

    fun getLiveListTimetable(day: String): LiveData<List<TimetableEntity>> {
        liveList = appDb.timetableDao().getLiveList(day)
        return liveList
    }

    fun addTimetable(timetableEntity: TimetableEntity) {
        AddAsyncTask(appDb).execute(timetableEntity)
    }


    class AddAsyncTask(db: AppDatabase) : AsyncTask<TimetableEntity, Void, Void>() {
        private var timetableDB = db
        override fun doInBackground(vararg params: TimetableEntity): Void? {
            timetableDB.timetableDao().insert(params[0])
            return null
        }

    }

    fun removeTimetableEntry(timetableEntity: TimetableEntity) {
        RemoveAsyncTask(appDb).execute(timetableEntity)
    }


    class RemoveAsyncTask(db: AppDatabase) : AsyncTask<TimetableEntity, Void, Void>() {
        private var timetableDB = db
        override fun doInBackground(vararg params: TimetableEntity): Void? {
            timetableDB.timetableDao().deleteRecord(params[0])
            return null
        }

    }

}