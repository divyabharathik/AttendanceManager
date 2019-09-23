package divya.com.attendancemanager.RoomData

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy
import androidx.room.Update



@Dao
interface DailyDao {

    @Delete
    fun deleteRecord(dailyEntity: DailyEntity)

    @Query("SELECT * from daily_table where day=:day order by subject_name")
    fun getDayTimetable(day: String): LiveData<List<DailyEntity>>

    @Query("SELECT * from daily_table where date=:str order by subject_name")
    fun getDateDetails(str: String): LiveData<List<DailyEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dailyEntity: DailyEntity)

    @Query("UPDATE daily_table SET present_absent_cancel = :p_a_c WHERE day = :day and date = :date and subject_name = :subject and `from`=:fromm and `to`= :too")
    fun update(p_a_c:String,day:String,date:String,subject:String,fromm:String,too:String)

    @Query("SELECT count(*) from daily_table where subject_name=:subject and present_absent_cancel=:status")
    fun getCount(subject: String,status: String):Int

}