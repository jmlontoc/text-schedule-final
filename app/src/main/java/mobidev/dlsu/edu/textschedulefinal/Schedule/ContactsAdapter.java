package mobidev.dlsu.edu.textschedulefinal.Schedule;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import mobidev.dlsu.edu.textschedulefinal.Contacts.Contact;
import mobidev.dlsu.edu.textschedulefinal.Contacts.ContactAdapter;
import mobidev.dlsu.edu.textschedulefinal.R;

/**
 * Created by Nobody on 12/14/2017.
 */

public class ContactsAdapter  extends RecyclerView.Adapter<ContactsAdapter.ContactsHolder> {
    private ArrayList<Contact> contacts;

    public ContactsAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }


    @Override
    public ContactsAdapter.ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.contacts_item, parent, false
        );

        return new ContactsAdapter.ContactsHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ContactsHolder holder, int position) {

        Contact c = contacts.get(position);

        holder.tvName.setText(c.getDisplayName());
        holder.tvNumber.setText(c.getNumber());
        Bundle bundle = new Bundle();
        bundle.putString("name",c.getDisplayName());
        bundle.putString("number",c.getNumber());
        holder.itemView.setTag(bundle);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass id to caller
                onItemClickListener.onItemClick((Bundle) v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public class ContactsHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber;

        public ContactsHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(Bundle bundle);
    }

    private ContactsAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ContactsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
