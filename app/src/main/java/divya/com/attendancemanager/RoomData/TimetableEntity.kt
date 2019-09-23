package divya.com.attendancemanager.RoomData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_table" , primaryKeys = ["day","from","to"])
data class TimetableEntity(
    @ColumnInfo(name = "day") var day: String,
    @ColumnInfo(name = "subject_name") var subject_name: String,
    @ColumnInfo(name = "from") var from: String,
    @ColumnInfo(name = "to") var to: String
)