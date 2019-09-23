package divya.com.attendancemanager.Utils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import divya.com.attendancemanager.RoomData.DailyEntity;

public class MyDiffCallback extends DiffUtil.Callback {

    private List<DailyEntity> oldList;
    private List<DailyEntity> newList;

    public MyDiffCallback(List<DailyEntity> newDailyEntity, List<DailyEntity> oldDailyEntity) {
        this.oldList = oldDailyEntity;
        this.newList = newDailyEntity;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
