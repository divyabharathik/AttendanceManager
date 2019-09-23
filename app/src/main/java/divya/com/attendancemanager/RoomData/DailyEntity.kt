package divya.com.attendancemanager.RoomData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "daily_table" ,primaryKeys = ["day","date","subject_name","from","to"])
data class DailyEntity(
    @ColumnInfo(name = "day") var day: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "subject_name") var subject_name: String,
    @ColumnInfo(name = "from") var from: String,
    @ColumnInfo(name = "to") var to: String,
    @ColumnInfo(name = "present_absent_cancel") var present_absent_cancel: String
)