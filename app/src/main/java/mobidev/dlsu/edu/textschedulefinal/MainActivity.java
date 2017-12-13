package mobidev.dlsu.edu.textschedulefinal;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import mobidev.dlsu.edu.textschedulefinal.AutoReply.AutoReplyActivity;
import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;

public class MainActivity extends AppCompatActivity {

    Button statusTab, scheduleTab, autoReplyTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTab = findViewById(R.id.status_tab);
        scheduleTab = findViewById(R.id.schedule_tab);
        autoReplyTab = findViewById(R.id.auto_reply_tab);

        // once the tabs are clicked

        autoReplyTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AutoReplyActivity.class);
                startActivity(i);
            }
        });

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
