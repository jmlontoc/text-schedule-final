package mobidev.dlsu.edu.textschedulefinal.Status;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import mobidev.dlsu.edu.textschedulefinal.AutoReply.AutoReplyActivity;
import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;
import mobidev.dlsu.edu.textschedulefinal.Contacts.ContactAdapter;
import mobidev.dlsu.edu.textschedulefinal.Helper;
import mobidev.dlsu.edu.textschedulefinal.R;

public class ModifyStatus extends AppCompatActivity {


    private ArrayList<Contact> contactsCart;

    RecyclerView rvContacts;
    EditText etMessage, etReply;
    Button submitBtn, deleteBtn;

    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_status);

        rvContacts = findViewById(R.id.rv_contacts2);
        etMessage = findViewById(R.id.et_status1);
        etReply = findViewById(R.id.et_reply_status1);
        submitBtn = findViewById(R.id.btn_submit_status1);
        deleteBtn = findViewById(R.id.btn_delete_status);
        back_button = findViewById(R.id.back);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), StatusActivity.class);
                startActivity(i);
            }
        });

        contactsCart = new ArrayList<>();

        final long id = getIntent().getExtras().getLong("modify");

        display();

        StatusDBHelper helper = new StatusDBHelper(getBaseContext());

        Status s = helper.getStatus(id);

        Log.i("asd", s.getstatus() + " " + s.getReply());

        etMessage.setText(s.getstatus());
        etReply.setText(s.getReply());


        // delete
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StatusDBHelper db = new StatusDBHelper(getBaseContext());

                db.deleteStatus(id);

                finish();

            }
        });

        // submit edited thing
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
                    StatusDBHelper dbHelper = new StatusDBHelper(getBaseContext());

                    Status s = new Status(reply, message, 1);
                    s.setContacts(contactsCart);

                    // add to db

                    dbHelper.editStatus(s, id);

                    finish();
                }
            }
        });

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
}
