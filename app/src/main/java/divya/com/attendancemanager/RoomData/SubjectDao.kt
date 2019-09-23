package divya.com.attendancemanager.RoomData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SubjectDao {


    @Query("DELETE FROM subjects_table")
    fun deleteAll()

    @Delete
    fun deleteSubject(vararg users: SubjectEntity)

    @Query("SELECT * from subjects_table")
    fun getAllSubjects(): LiveData<List<SubjectEntity>>

    @Query("SELECT subject_name from subjects_table order by subject_name")
    fun getAllSubjectsAscending(): Array<String>

    @Insert
    fun insert(subjectEntity: SubjectEntity)
}