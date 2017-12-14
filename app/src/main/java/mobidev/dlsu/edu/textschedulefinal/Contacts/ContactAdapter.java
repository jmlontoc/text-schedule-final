package mobidev.dlsu.edu.textschedulefinal.Contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import mobidev.dlsu.edu.textschedulefinal.R;

/**
 * Created by user on 12/12/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private ArrayList<Contact> contacts;

    public ContactAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }


    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.contact_item, parent, false
        );

        return new ContactHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {

        Contact c = contacts.get(position);

        holder.tvName.setText(c.getDisplayName());
        holder.tvNumber.setText(c.getNumber());
        holder.btnAddContact.setTag(c);

        holder.btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact c = (Contact) view.getTag();
                onItemClickListener.onItemClick(c);

                view.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public class ContactHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber;
        Button btnAddContact;

        public ContactHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNumber = itemView.findViewById(R.id.tv_number);
            btnAddContact = itemView.findViewById(R.id.btn_add_contact);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(Contact contact);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
