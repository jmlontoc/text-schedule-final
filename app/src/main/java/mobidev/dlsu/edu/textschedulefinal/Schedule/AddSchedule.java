package mobidev.dlsu.edu.textschedulefinal.Schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import mobidev.dlsu.edu.textschedulefinal.R;

public class AddSchedule extends AppCompatActivity {
    String smsNumber, smsText, smsName;
    private PendingIntent pi;
    Integer hour,minute,day,month,year;
    int request;
    TimePicker tptime;
    DatePicker dpDate;
    TextView tvSmsName, tvSmsNumber;
    EditText etSmsText;
    ScheduleDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        dbHelper = new ScheduleDatabaseHelper(getBaseContext());
        tptime = (TimePicker)findViewById(R.id.tp_time);
        tptime.setIs24HourView(true);
        dpDate= (DatePicker) findViewById(R.id.cv_calendar);
        Button buttonSendSms = (Button)findViewById(R.id.sendsms);
        tvSmsName = (TextView)findViewById(R.id.smsname);
        tvSmsNumber = (TextView)findViewById(R.id.smsnumber);
        smsName = getIntent().getExtras().getString("name");
        smsNumber = getIntent().getExtras().getString("number");
        tvSmsName.setText(smsName);
        tvSmsNumber.setText(smsNumber);
        buttonSendSms.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {


                etSmsText = (EditText)findViewById(R.id.smstext);
                smsText = etSmsText.getText().toString();

                hour=tptime.getCurrentHour();
                minute=tptime.getCurrentMinute();
                day=dpDate.getDayOfMonth();
                month=dpDate.getMonth();
                year=dpDate.getYear();


                Intent i = new Intent(getBaseContext(),AlarmReceiver.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("smsNumber", smsNumber);
                bundle.putCharSequence("smsText", smsText);
                i.putExtras(bundle);
                int alarmid = (int)System.currentTimeMillis();
                request = alarmid;
                pi=PendingIntent.getBroadcast(getBaseContext(),alarmid,i,PendingIntent.FLAG_UPDATE_CURRENT);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, hour, minute, 0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                Schedule schedule = new Schedule();
                schedule.setNumber(smsNumber);
                schedule.setContent(smsText);
                schedule.setDate(calendar.getTimeInMillis());
                schedule.setRequest(request);
                dbHelper.addSchedule(schedule);
                Toast.makeText(getBaseContext() ,
                        "Start Alarm with \n"
                                + "smsNumber = " + smsNumber + "\n"
                                + "smsText = " + smsText + "\n"
                                + "hour = " + hour + "\n"
                                + "minute = " +minute+"\n",
                        Toast.LENGTH_LONG).show();
                finish();
            }});
    }
}
