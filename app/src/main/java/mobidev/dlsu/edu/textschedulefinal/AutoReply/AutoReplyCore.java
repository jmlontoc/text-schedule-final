package mobidev.dlsu.edu.textschedulefinal.AutoReply;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by user on 12/13/2017.
 */

public class AutoReplyCore extends BroadcastReceiver{

    public static final int REQUEST_SMS_PERMISSIONS = 0;


    @Override
    public void onReceive(Context context, Intent intent) {



    }

    public boolean checkSMSPermission(Context context) {

        return ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
                        == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED;
    }
}
