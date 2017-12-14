package mobidev.dlsu.edu.textschedulefinal.Schedule;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import mobidev.dlsu.edu.textschedulefinal.CursorRecyclerViewAdapter;
import mobidev.dlsu.edu.textschedulefinal.R;

/**
 * Created by Nobody on 12/12/2017.
 */

public class ScheduleAdapter extends CursorRecyclerViewAdapter<ScheduleAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;

    public ScheduleAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        // cursor is already pointed at the current position
        long id = cursor.getLong(cursor.getColumnIndex(Schedule.COLUMN_ID));
        String number = cursor.getString(cursor.getColumnIndex(Schedule.COLUMN_NUMBER));
        String text = cursor.getString(cursor.getColumnIndex(Schedule.COLUMN_CONTENT));
        Long date = cursor.getLong(cursor.getColumnIndex(Schedule.COLUMN_DATE));
        int request = cursor.getInt(cursor.getColumnIndex(Schedule.COLUMN_REQUEST));
        Date d = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        String stringDate = formatter.format(d);
        viewHolder.tvNumber.setText(number);
        viewHolder.tvText.setText(text);
        viewHolder.tvDate.setText(stringDate);

        // set the database id to the viewholder's itemView (the "whole row" view)
        viewHolder.itemView.setTag(id);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass id to caller
                onItemClickListener.onItemClick((Long) v.getTag());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNumber, tvDate, tvText;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvText = (TextView) itemView.findViewById(R.id.tv_text);

        }
    }

    // interface to be implemented to know if an item has been clicked or not
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(long id);
    }
}
