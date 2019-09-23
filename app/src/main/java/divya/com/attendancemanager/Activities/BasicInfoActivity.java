package divya.com.attendancemanager.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import divya.com.attendancemanager.R;
import divya.com.attendancemanager.RoomData.PreferenceConnector;

public class BasicInfoActivity extends AppCompatActivity {
    SeekBar attendance;
    TextView txt, begin, end;
    TextInputEditText name;
    String avatar;
    CardView girl, boy;
    String selected_begin = "",selected_end = "";
    int bYear,bMonth,bDay,eYear,eMonth,eDay;
    String[] month = {"Jan","Feb","Mar","Apr","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    MaterialButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        avatar = "girl";
        attendance = findViewById(R.id.attendance_seekbar);
        txt = findViewById(R.id.percentage_tv);
        name = findViewById(R.id.name_editText);
        girl = findViewById(R.id.girl);
        boy = findViewById(R.id.boy);
        saveBtn = findViewById(R.id.save_mt_btn);
        begin = findViewById(R.id.begin_sem_tv);
        end = findViewById(R.id.end_sem_tv);
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatar = "girl";
                girl.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                boy.setCardBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatar = "boy";
                boy.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                girl.setCardBackgroundColor(getResources().getColor(R.color.white));
            }
        });
//        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BasicInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                selected_begin = dayOfMonth + " " + month[monthOfYear-1] + " " + year;
                                bYear = year;
                                bMonth = monthOfYear;
                                bDay = dayOfMonth;
                                begin.setText(selected_begin);
                                begin.setTextColor(getResources().getColor(R.color.colorAccent));
                            }
                        }, bYear, bMonth, bDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BasicInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                selected_end = dayOfMonth + " " + month[monthOfYear-1] + " " + year;
                                eYear = year;
                                eMonth = monthOfYear;
                                eDay = dayOfMonth;
                                end.setText(selected_end);
                                end.setTextColor(getResources().getColor(R.color.colorAccent));
                            }
                        }, eYear, eMonth, eDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        attendance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt.setText("" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.requireNonNull(name.getText()).toString().equals("")) {
                    Toast.makeText(BasicInfoActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                } else if (begin.getText().equals("Choose date") && end.getText().equals("Choose date")){
                    Toast.makeText(BasicInfoActivity.this, "Choose the Semester beginning and end date", Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(BasicInfoActivity.this, SubjectsActivity.class);
                    PreferenceConnector.writeString(BasicInfoActivity.this, PreferenceConnector.Name, name.getText().toString());
                    PreferenceConnector.writeString(BasicInfoActivity.this, PreferenceConnector.Criteria, attendance.getProgress() + "");
                    PreferenceConnector.writeString(BasicInfoActivity.this, PreferenceConnector.Gender, avatar);
                    PreferenceConnector.writeString(BasicInfoActivity.this,PreferenceConnector.BEGIN,selected_begin);
                    PreferenceConnector.writeString(BasicInfoActivity.this,PreferenceConnector.END,selected_end);
                    startActivity(i);
                }
            }
        });

    }
}
