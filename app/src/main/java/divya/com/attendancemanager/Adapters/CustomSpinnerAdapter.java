package divya.com.attendancemanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import divya.com.attendancemanager.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;

    ArrayList<String> list;
    LayoutInflater inflater;

    public CustomSpinnerAdapter(Context applicationContext,  ArrayList<String> countryNames) {
        this.context = applicationContext;

        this.list = countryNames;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return  0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_items, null);

        TextView names = (TextView) view.findViewById(R.id.textView);

        names.setText(list.get(i));
        return view;
    }
}
