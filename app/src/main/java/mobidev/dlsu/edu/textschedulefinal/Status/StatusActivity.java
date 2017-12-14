package mobidev.dlsu.edu.textschedulefinal.Status;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import mobidev.dlsu.edu.textschedulefinal.Helper;
import mobidev.dlsu.edu.textschedulefinal.R;

public class StatusActivity extends AppCompatActivity {

    RecyclerView rvStatus;
    Button addStatusBtn;
    StatusDBHelper db;
    StatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        rvStatus = findViewById(R.id.rv_status);
        addStatusBtn = findViewById(R.id.btn_add_status);
        db = new StatusDBHelper(getBaseContext());
        adapter = new StatusAdapter(getBaseContext(), db.getAllStatuses());

        rvStatus.setAdapter(adapter);
        rvStatus.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        // add status

        addStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AddStatus.class);
                startActivity(i);
            }
        });

        // edit status

        adapter.setOnItemClickListener(new StatusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long id) {

                Intent i = new Intent(getBaseContext(), ModifyStatus.class);
                i.putExtra("modify", id);
                startActivity(i);
            }
        });

    }

    public void update() {
        adapter.changeCursor(db.getAllStatuses());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        update();
    }
}
