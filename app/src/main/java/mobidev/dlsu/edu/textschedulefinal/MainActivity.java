package mobidev.dlsu.edu.textschedulefinal;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button tvAdd;
    ScheduleDatabaseHelper dbHelper;
    ScheduleAdapter scheduleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent = new Intent(getBaseContext(), AddSchedule.class);
                // Note: make sure to pass an extra that we want to add and not edit
                intent.putExtra("add", true);

                // END

                startActivity(intent);
            }

        });

        scheduleAdapter.setOnItemClickListener(new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long id) {
                // lead the user to the activity to view the clicked item
                //Intent intent = new Intent(getBaseContext(), ViewActivitySkeleton.class);
                // Note: make sure to pass an extra that we want to edit and not add
                //intent.putExtra("add", false);
                //intent.putExtra("id", id);
                //       also pass the id of the selected item

                // END
                //startActivity(intent);

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
}
