package mobidev.dlsu.edu.textschedulefinal.Status;

import android.content.Context;
import android.database.Cursor;
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

public class StatusAdapter extends CursorRecyclerViewAdapter<StatusAdapter.ViewHolder> {

    // private OnItemClickListener onItemClickListener;

    public StatusAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(StatusAdapter.ViewHolder viewHolder, Cursor cursor) {

        final long id = cursor.getLong(cursor.getColumnIndex(Status.COLUMN_ID));
        String message = cursor.getString(cursor.getColumnIndex(Status.COLUMN_STATUS));
        String reply = cursor.getString(cursor.getColumnIndex(Status.COLUMN_REPLY));

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
    public StatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_item, parent, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage, tvReply;
        View layout;

        public ViewHolder(View itemView) {

            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_status_message);
            tvReply = itemView.findViewById(R.id.tv_status_reply);
            layout = itemView.findViewById(R.id.status_layout);

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
