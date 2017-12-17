package mobidev.dlsu.edu.textschedulefinal.AutoReply;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;
import mobidev.dlsu.edu.textschedulefinal.CursorRecyclerViewAdapter;
import mobidev.dlsu.edu.textschedulefinal.R;

/**
 * Created by user on 12/4/2017.
 */

public class AutoReplyAdapter extends CursorRecyclerViewAdapter<AutoReplyAdapter.ViewHolder> {

    // private OnItemClickListener onItemClickListener;

    public AutoReplyAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(AutoReplyAdapter.ViewHolder viewHolder, Cursor cursor) {

        final long id = cursor.getLong(cursor.getColumnIndex(AutoReply.COLUMN_ID));
        String message = cursor.getString(cursor.getColumnIndex(AutoReply.COLUMN_MESSAGE));
        String reply = cursor.getString(cursor.getColumnIndex(AutoReply.COLUMN_REPLY));

        //TODO paolo


        int isActive = cursor.getInt(cursor.getColumnIndex(AutoReply.COLUMN_ACTIVE));

        if (isActive != 0) {
            viewHolder.tvMessage.setTextColor(Color.parseColor("#807d7d"));
        }

        viewHolder.tvMessage.setText(message);
        viewHolder.tvReply.setText(reply);
        viewHolder.layout.setTag(id);

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick((Long) view.getTag());
            }
        });

    }

    @Override
    public AutoReplyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.auto_reply_item, parent, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage, tvReply;
        View layout;

        public ViewHolder(View itemView) {

            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_ar_message);
            tvReply = itemView.findViewById(R.id.tv_ar_reply);
            layout = itemView.findViewById(R.id.ar_layout);

        }
    }

    public interface OnItemClickListener {

        public void onItemClick(long id);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
