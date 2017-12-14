package mobidev.dlsu.edu.textschedulefinal;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 12/13/2017.
 */

public class Helper {

    public static void easierToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
