package mobidev.dlsu.edu.textschedulefinal.AutoReply;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;
import mobidev.dlsu.edu.textschedulefinal.Contacts.ContactAdapter;
import mobidev.dlsu.edu.textschedulefinal.Helper;
import mobidev.dlsu.edu.textschedulefinal.MainActivity;
import mobidev.dlsu.edu.textschedulefinal.R;

import static android.Manifest.permission.READ_CONTACTS;

public class AddAutoReply extends AppCompatActivity {

    public final static int REQUEST_READ_CONTACTS = 0;
    private ArrayList<Contact> contactsCart;

    RecyclerView rvContacts;
    EditText etMessage, etReply;
    Button submitBtn;
    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auto_reply);

        rvContacts = findViewById(R.id.rv_contacts);
        etMessage = findViewById(R.id.et_message);
        etReply = findViewById(R.id.et_reply);
        submitBtn = findViewById(R.id.btn_submit_ar);

        back_button = findViewById(R.id.back);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AutoReplyActivity.class);
                startActivity(i);
            }
        });

        contactsCart = new ArrayList<>();

        start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                start();
            }
            else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Helper.easierToast("Sorry, this app wont work if you do not grant the permission",
                        getBaseContext());
            }
        }
    }

    public boolean checkContactsPermission() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED);
    }

    public void start() {
        if (checkContactsPermission()) {
            display();

            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String message = etMessage.getText().toString().trim();
                    String reply = etReply.getText().toString().trim();

                    //error checking
                    int errorCount = 0;

                    if (contactsCart.size() == 0) {
                        Helper.easierToast("Please add contact/s", getBaseContext());
                        errorCount++;
                    }

                    if (message.equals("")) {
                        Helper.easierToast("Please fill up all fields", getBaseContext());
                        errorCount++;
                    }

                    if (reply.equals("")) {
                        Helper.easierToast("Please fill up all fields", getBaseContext());
                        errorCount++;
                    }

                    if (errorCount == 0) {
                        AutoReplyDBHelper dbHelper = new AutoReplyDBHelper(getBaseContext());

                        AutoReply autoReply = new AutoReply(reply, message, 1);
                        autoReply.setContacts(contactsCart);

                        // add to db

                        dbHelper.addAutoReply(autoReply);

                        finish();
                    }
                }
            });
        }
        else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS
            );
        }
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

    public void display() {
        ContactAdapter adapter = new ContactAdapter(getContacts());

        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact contact) {

                contactsCart.add(contact);

            }
        });
    }


}
