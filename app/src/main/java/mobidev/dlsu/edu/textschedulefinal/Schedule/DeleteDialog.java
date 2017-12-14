package mobidev.dlsu.edu.textschedulefinal.Schedule;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import mobidev.dlsu.edu.textschedulefinal.MainActivity;
import mobidev.dlsu.edu.textschedulefinal.R;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Nobody on 12/12/2017.
 */

public class DeleteDialog extends DialogFragment {
    ScheduleDatabaseHelper dbHelper;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dbHelper = new ScheduleDatabaseHelper(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to delete?")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle bundle = getArguments();
                        Long id = bundle.getLong("id", 0);
                        Schedule schedule = dbHelper.getSchedule(id);
                        int request = schedule.getRequest();
                        Intent intent = new Intent(getContext(),AlarmReceiver.class);
                        PendingIntent sender = PendingIntent.getBroadcast(getContext(), request, intent, 0);
                        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(sender);
                        //TODO Delete in alarm manager
                        dbHelper.deleteSchedule(id);
                        ((MainActivity)getActivity()).updateList();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        return builder.create() ;
    }
}
