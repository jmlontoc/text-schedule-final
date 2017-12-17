package mobidev.dlsu.edu.textschedulefinal;

import android.content.Intent;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import mobidev.dlsu.edu.textschedulefinal.Schedule.AddSchedule;
import mobidev.dlsu.edu.textschedulefinal.Schedule.DeleteDialog;
import mobidev.dlsu.edu.textschedulefinal.Schedule.Schedule;
import mobidev.dlsu.edu.textschedulefinal.Schedule.ScheduleAdapter;
import mobidev.dlsu.edu.textschedulefinal.Schedule.ScheduleDatabaseHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import mobidev.dlsu.edu.textschedulefinal.AutoReply.AutoReplyActivity;
import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;
import mobidev.dlsu.edu.textschedulefinal.Schedule.SelectContact;
import mobidev.dlsu.edu.textschedulefinal.Status.Status;
import mobidev.dlsu.edu.textschedulefinal.Status.StatusActivity;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button tvAdd;
    ScheduleDatabaseHelper dbHelper;
    ScheduleAdapter scheduleAdapter;

    TextView statusTab, scheduleTab, autoReplyTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTab = findViewById(R.id.status_tab);
        scheduleTab = findViewById(R.id.schedule_tab);
        autoReplyTab = findViewById(R.id.auto_reply_tab);

        // once the tabs are clicked

        // auto reply
        autoReplyTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AutoReplyActivity.class);
                startActivity(i);
            }
        });

        //status
        statusTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), StatusActivity.class);
                startActivity(i);
            }
        });


        // scheduler here
        //TODO

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        tvAdd = (Button) findViewById(R.id.btn_add);

        dbHelper = new ScheduleDatabaseHelper(getBaseContext());
        scheduleAdapter
                = new ScheduleAdapter(getBaseContext(),
                dbHelper.getAllScheduleCursor());
        recyclerView.setAdapter(scheduleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));


        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lead the user to the activity to create a new record
                Intent intent = new Intent(getBaseContext(), SelectContact.class);
                // Note: make sure to pass an extra that we want to add and not edit
                intent.putExtra("add", true);

                // END

                startActivity(intent);
            }

        });

        scheduleAdapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long id) {


                DeleteDialog dd = new DeleteDialog();
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                dd.setArguments(bundle);

                dd.show(getSupportFragmentManager(), "");
            }
        });
    }
    public void updateList(){
        scheduleAdapter.changeCursor(dbHelper.getAllScheduleCursor());
        scheduleAdapter.notifyDataSetChanged();
    }
    protected void onResume() {
        super.onResume();
        scheduleAdapter.changeCursor(dbHelper.getAllScheduleCursor());
    }

    public ArrayList<Contact> getContacts() {
        ContentResolver cr = getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ArrayList<Contact> alContacts = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext())
                    {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY));

                        Contact contact = new Contact(contactName, contactNumber);

                        alContacts.add(contact);
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext());
        }

        return alContacts;
    }

    public boolean checkSMSPermission() {

        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                        == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED;
    }


}
