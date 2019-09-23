package divya.com.attendancemanager.RoomData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [(SubjectEntity::class),(TimetableEntity::class),(DailyEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDataBase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "subjects_db").allowMainThreadQueries().build()
            }
            return INSTANCE as AppDatabase
        }
    }

    abstract fun subjectDao(): SubjectDao

    abstract fun timetableDao(): TimetableDao

    abstract fun dailyDao(): DailyDao

}
