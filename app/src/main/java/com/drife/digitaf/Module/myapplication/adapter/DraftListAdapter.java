package com.drife.digitaf.Module.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drife.digitaf.Module.InputKredit.HasilPerhitungan.Activity.HasilPerhitungandraft;
import com.drife.digitaf.Module.myapplication.childfragment.DraftFragment;
import com.drife.digitaf.Module.myapplication.model.DraftItem;
import com.drife.digitaf.Module.myapplication.model.FormValue;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.R;
import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class DraftListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Draft> listItems;
    private DraftFragment draftFragment;

    public DraftListAdapter(Context context, List<Draft> listItems, DraftFragment draftFragment) {
        this.context = context;
        this.listItems = listItems;
        this.draftFragment = draftFragment;
    }

    //View holder for item layout
    public class DraftViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtLastSaved;
        public ImageView imgEdit;
        public CardView lyDraft;

        public DraftViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtLastSaved = (TextView) itemView.findViewById(R.id.txtLastSaved);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            lyDraft = itemView.findViewById(R.id.lyDraft);

//            imgEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, MinimumCustomerDataActivity.class);
//                    ((Activity)context).startActivityForResult(intent, 1);
//                }
//            });
        }
    }

    //Function to define which layout to create/inflate
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_list_draft_item, parent, false);
        return new DraftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Draft draftItem = getItemAt(position);

        DraftViewHolder draftViewHolder = (DraftViewHolder) holder;

        draftViewHolder.txtName.setText(!draftItem.getNamaCustomer().equals("") ? draftItem.getNamaCustomer() : "-");
        draftViewHolder.txtLastSaved.setText("last saved: " + chageDateFormat(draftItem.getLastSaved(), "sent"));

        draftViewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Draft draftItem =listItems.get(position);

                draftFragment.showUploadPage(draftItem, position);
//                ((Activity)context).startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //Funtion to get adapter data from selected position
    public Draft getItemAt(int position) {
        return this.listItems.get(position);
    }

    public String chageDateFormat(String date, String formatDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
        SimpleDateFormat newFormat = new SimpleDateFormat(formatDate.equals("sent") ? "dd MMM yyyy, kk:mm" : "dd MMM yyyy");

        try {
            return newFormat.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
