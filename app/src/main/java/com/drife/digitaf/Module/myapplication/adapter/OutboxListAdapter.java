package com.drife.digitaf.Module.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drife.digitaf.Module.Offline.SubmitService;
import com.drife.digitaf.Module.myapplication.childfragment.OutboxFragment;
import com.drife.digitaf.ORM.Database.Outbox_submit;
import com.drife.digitaf.R;

import java.util.List;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class OutboxListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Outbox_submit> listItems;
    private OutboxFragment outboxFragment;

    public OutboxListAdapter(Context context, List<Outbox_submit> listItems, OutboxFragment outboxFragment) {
        this.context = context;
        this.listItems = listItems;
        this.outboxFragment = outboxFragment;
    }

    //View holder for item layout
    public class DraftViewHolder extends RecyclerView.ViewHolder{
        public TextView txtName, txtLastAttempt;
        public ImageView imgUpload;

        public DraftViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtLastAttempt = (TextView) itemView.findViewById(R.id.txtLastAttempt);
            imgUpload = (ImageView) itemView.findViewById(R.id.imgUpload);

        }

    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    //Function to define which layout to create/inflate
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_list_outbox_item, parent, false);
        return new DraftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Outbox_submit outbox_submit = getItemAt(position);

        DraftViewHolder draftViewHolder = (DraftViewHolder) holder;

        draftViewHolder.txtName.setText(outbox_submit.getCustomerName());
        draftViewHolder.txtLastAttempt.setText("last attempt: " + outbox_submit.getDate());
        draftViewHolder.imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage("Submit?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Outbox_submit outbox_submit = listItems.get(position);
                                Log.d("name", outbox_submit.getCustomerName());
                                outboxFragment.showUploadPage(outbox_submit);
                                dialogInterface.dismiss();
                            }
                        }
                        ).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }
                        );
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //Funtion to get adapter data from selected position
    public Outbox_submit getItemAt(int position) {
        return this.listItems.get(position);
    }
}
