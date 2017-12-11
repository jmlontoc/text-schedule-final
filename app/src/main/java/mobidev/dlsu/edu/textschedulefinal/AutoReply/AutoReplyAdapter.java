package mobidev.dlsu.edu.textschedulefinal.AutoReply;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import mobidev.dlsu.edu.textschedulefinal.CursorRecyclerViewAdapter;

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

    }

    @Override
    public AutoReplyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
