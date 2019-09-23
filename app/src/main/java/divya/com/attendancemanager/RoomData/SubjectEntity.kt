package divya.com.attendancemanager.RoomData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects_table")
data class SubjectEntity(
    @PrimaryKey @ColumnInfo(name = "subject_name") var subject_name: String
)