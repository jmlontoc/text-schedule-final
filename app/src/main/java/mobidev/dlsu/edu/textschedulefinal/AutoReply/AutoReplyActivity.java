package mobidev.dlsu.edu.textschedulefinal.AutoReply;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import mobidev.dlsu.edu.textschedulefinal.Helper;
import mobidev.dlsu.edu.textschedulefinal.R;

public class AutoReplyActivity extends AppCompatActivity {

    RecyclerView rvAutoReplies;
    Button addAutoReply;
    AutoReplyDBHelper db;
    AutoReplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_reply);

        rvAutoReplies = findViewById(R.id.rv_auto_reply);
        db = new AutoReplyDBHelper(getBaseContext());

        adapter = new AutoReplyAdapter(getBaseContext(), db.getAllAutoReplies());

        rvAutoReplies.setAdapter(adapter);
        rvAutoReplies.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        // add
        addAutoReply = findViewById(R.id.btn_add);
        addAutoReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AddAutoReply.class);
                i.putExtra("add", true);
                startActivity(i);
            }
        });


        adapter.setOnItemClickListener(new AutoReplyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long id) {
                // Helper.easierToast("it went here", getBaseContext());
                Intent i = new Intent(getBaseContext(), ModifyAutoReply.class);
                i.putExtra("modify", id);
                startActivity(i);
            }
        });
    }

    public void update() {
        adapter.changeCursor(db.getAllAutoReplies());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        update();
    }
}
