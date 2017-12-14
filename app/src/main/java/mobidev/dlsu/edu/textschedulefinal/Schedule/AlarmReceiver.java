package mobidev.dlsu.edu.textschedulefinal.Schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;

import mobidev.dlsu.edu.textschedulefinal.MainActivity;

/**
 * Created by Nobody on 12/12/2017.
 */

public class AlarmReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent){
        Bundle bundle = intent.getExtras();
        String smsNumber = (String) bundle.getCharSequence("smsNumber");
        String smsText = (String) bundle.getCharSequence("smsText");
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsNumber, null, smsText, null, null);
    }
}
