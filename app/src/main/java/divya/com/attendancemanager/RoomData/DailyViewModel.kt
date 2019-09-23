package divya.com.attendancemanager.RoomData

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class DailyViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var listOfDayOfWeek: LiveData<List<DailyEntity>>
    lateinit var dateSpecifiedList: LiveData<List<DailyEntity>>

    private val appDb: AppDatabase = AppDatabase.getDataBase(this.getApplication())

    fun getDateSpecificList(string: String): LiveData<List<DailyEntity>> {
        dateSpecifiedList = appDb.dailyDao().getDateDetails(string)
        println("date------------->$string")
        return dateSpecifiedList
    }

    fun getCount(subject:String,status:String): Int{
        return appDb.dailyDao().getCount(subject,status)
    }

    fun addDailyData(DailyEntity: DailyEntity) {
        AddAsyncTask(appDb).execute(DailyEntity)
    }


    class AddAsyncTask(db: AppDatabase) : AsyncTask<DailyEntity, Void, Void>() {
        private var dB = db
        override fun doInBackground(vararg params: DailyEntity): Void? {
            dB.dailyDao().insert(params[0])
            return null
        }

    }

    fun update_daily(dailyEntity: DailyEntity) {
        UpdateAsyncTask(appDb).execute(dailyEntity)
    }


    class UpdateAsyncTask(db: AppDatabase) : AsyncTask<DailyEntity, Void, Void>() {
        private var timetableDB = db
        override fun doInBackground(vararg params: DailyEntity): Void? {
            timetableDB.dailyDao().update(
                params[0].present_absent_cancel,
                params[0].day,
                params[0].date,
                params[0].subject_name,
                params[0].from,
                params[0].to
            )
            return null
        }

    }

    class RemoveAsyncTask(db: AppDatabase) : AsyncTask<DailyEntity, Void, Void>() {
        private var timetableDB = db
        override fun doInBackground(vararg params: DailyEntity): Void? {
            timetableDB.dailyDao().deleteRecord(params[0])
            return null
        }

    }

}