package divya.com.attendancemanager.RoomData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TimetableDao {

    @Delete
    fun deleteRecord(vararg timetableEntity: TimetableEntity)

    @Query("SELECT * from time_table where day =:givenDay")
    fun getLiveList(givenDay : String): LiveData<List<TimetableEntity>>

    @Query("SELECT * from time_table where day=:givenDay")
    fun getList(givenDay : String): List<TimetableEntity>

    @Insert
    fun insert(timetableEntity: TimetableEntity)
}