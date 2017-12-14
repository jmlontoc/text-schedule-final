package mobidev.dlsu.edu.textschedulefinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import mobidev.dlsu.edu.textschedulefinal.AutoReply.AutoReply;
import mobidev.dlsu.edu.textschedulefinal.AutoReply.AutoReplyDBHelper;
import mobidev.dlsu.edu.textschedulefinal.Status.Status;
import mobidev.dlsu.edu.textschedulefinal.Status.StatusDBHelper;

/**
 * Created by user on 12/14/2017.
 */

public class SMSCore extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            Object[] pdus = (Object[]) bundle.get("pdus");

            for (Object pdu: pdus) {

                SmsMessage sms;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    sms = SmsMessage.createFromPdu((byte[]) pdu, bundle.getString("format"));
                }
                else {
                    sms = SmsMessage.createFromPdu((byte[]) pdu);
                }

                String sender = sms.getOriginatingAddress();
                String body = sms.getMessageBody();

                Log.i("what", "plase");

                autoReply(context, sender, body);
                statusReply(context, sender, body);
            }
        }
    }

    public void statusReply(Context context, String sender, String body) {
        StatusDBHelper db = new StatusDBHelper(context);

        Cursor cursor = db.getAllStatuses();
        sender = sender.trim();

        if (cursor.moveToFirst()) {

            do {
                String message = cursor.getString(cursor.getColumnIndex(Status.COLUMN_STATUS));
                String reply = cursor.getString(cursor.getColumnIndex(Status.COLUMN_REPLY));
                int isActive = cursor.getInt(cursor.getColumnIndex(Status.COLUMN_ACTIVE));
                long id = cursor.getLong(cursor.getColumnIndex(Status.COLUMN_ID));

                Log.i("wew", message + " " + body + " " + isActive);

                if (isActive == 0) {

                    if (body.toUpperCase().contains(message.toUpperCase())) {

                        sendStatusMessage(id, context, sender, reply);
                    }
                }

            }while(cursor.moveToNext());
        }
    }

    public void autoReply(Context context, String sender, String body) {
        AutoReplyDBHelper db = new AutoReplyDBHelper(context);

        Cursor cursor = db.getAllAutoReplies();
        sender = sender.trim();

        if (cursor.moveToFirst()) {

            do {
                String message = cursor.getString(cursor.getColumnIndex(AutoReply.COLUMN_MESSAGE));
                String reply = cursor.getString(cursor.getColumnIndex(AutoReply.COLUMN_REPLY));
                int isActive = cursor.getInt(cursor.getColumnIndex(AutoReply.COLUMN_ACTIVE));
                long id = cursor.getLong(cursor.getColumnIndex(AutoReply.COLUMN_ID));

                Log.i("wew", message + " " + body + " " + isActive);

                if (isActive == 0) {

                    if (body.toUpperCase().contains(message.toUpperCase())) {

                        sendToRecipients(id, context, sender, reply);
                    }
                }

            }while(cursor.moveToNext());
        }
    }

    public String convertToNumber(String number) {
        number = number.replaceAll("\\s","");
        number = number.substring(1);
        number = "+63" + number;

        return number;
    }

    public String justTrim(String number) {
        return number.replaceAll("\\s", "");
    }

    public String getFirstName(String name) {
        String [] split = name.split(" ");

        return split[0];

    }

    public void sendToRecipients(long id, Context context, String sender, String reply) {


        Cursor c = new AutoReplyDBHelper(context).getRecipients(id);

        if (c.moveToFirst()) {

            Log.i("wew", sender + " = = " + c.getString(c.getColumnIndex(AutoReply.COLUMN_CONTACT_NUMBER)));

            String x = convertToNumber(c.getString(c.getColumnIndex(AutoReply.COLUMN_CONTACT_NUMBER)));

            Log.i("compare", sender + " = = = " + x);

            do {

                if (c.getString(c.getColumnIndex(AutoReply.COLUMN_CONTACT_NUMBER)).contains("+")) {


                    if (sender.equalsIgnoreCase(
                            justTrim(
                                    c.getString(c.getColumnIndex(AutoReply.COLUMN_CONTACT_NUMBER))
                            )
                    )) {

                        Log.i("im close", "yep");

                        String name = c.getString(c.getColumnIndex(AutoReply.COLUMN_CONTACT_NAME));

                        SmsManager smsManager = SmsManager.getDefault();

                        smsManager.sendTextMessage(
                                sender,
                                null, reply + ", " + getFirstName(name),
                                null,
                                null

                        );

                    }
                }

                if (sender.equalsIgnoreCase(
                        convertToNumber(
                                c.getString(c.getColumnIndex(AutoReply.COLUMN_CONTACT_NUMBER))
                        )
                )) {

                    Log.i("im close", "yep");

                    String name = c.getString(c.getColumnIndex(AutoReply.COLUMN_CONTACT_NAME));

                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(
                            sender,
                            null,
                            reply + ", " + getFirstName(name),
                            null,
                            null

                    );
                }

            }while (c.moveToNext());
        }
    }

    public void sendStatusMessage(long id, Context context, String sender, String reply) {
        Cursor c = new StatusDBHelper(context).getStatusRecipients(id);

        if (c.moveToFirst()) {

            do {

                if (c.getString(c.getColumnIndex(Status.COLUMN_CONTACT_NUMBER)).contains("+")) {


                    if (sender.equalsIgnoreCase(
                            justTrim(
                                    c.getString(c.getColumnIndex(Status.COLUMN_CONTACT_NUMBER))
                            )
                    )) {

                        Log.i("im close", "yep");

                        String name = c.getString(c.getColumnIndex(Status.COLUMN_CONTACT_NAME));

                        SmsManager smsManager = SmsManager.getDefault();

                        smsManager.sendTextMessage(
                                sender,
                                null, reply + ", " + getFirstName(name),
                                null,
                                null

                        );

                    }
                }

                if (sender.equalsIgnoreCase(
                        convertToNumber(
                                c.getString(c.getColumnIndex(Status.COLUMN_CONTACT_NUMBER))
                        )
                )) {

                    Log.i("im close", "yep");

                    String name = c.getString(c.getColumnIndex(Status.COLUMN_CONTACT_NAME));

                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(
                            sender,
                            null,
                            reply + ", " + getFirstName(name),
                            null,
                            null

                    );
                }

            }while (c.moveToNext());
        }
    }
}
