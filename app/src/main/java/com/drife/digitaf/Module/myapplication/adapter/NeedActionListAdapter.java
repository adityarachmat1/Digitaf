package com.drife.digitaf.Module.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.Module.myapplication.childfragment.NeedActionFragment;
import com.drife.digitaf.Module.myapplication.model.NeedActionItem;
import com.drife.digitaf.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class NeedActionListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<InquiryItem> listItems;
    private NeedActionFragment needActionFragment;

    public NeedActionListAdapter(Context context, List<InquiryItem> listItems, NeedActionFragment needActionFragment) {
        this.context = context;
        this.listItems = listItems;
        this.needActionFragment = needActionFragment;
    }

    //View holder for item layout
    public class DraftViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtDocumentInfo, txtSent;
        public ImageView imgEdit;

        public DraftViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDocumentInfo = (TextView) itemView.findViewById(R.id.txtDocumentInfo);
            txtSent = (TextView) itemView.findViewById(R.id.txtSent);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
        }
    }

    //Function to define which layout to create/inflate
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_list_needaction_item, parent, false);
        return new DraftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        InquiryItem draftItem = listItems.get(position);

        DraftViewHolder draftViewHolder = (DraftViewHolder) holder;

        draftViewHolder.txtName.setText(draftItem.getFormValue().getCustomerName());
        draftViewHolder.txtSent.setText("sent: " + chageDateFormat(draftItem.getSent(), "sent"));

        if (draftItem.getFormValue().getDocument().getOptional() != null) {
            int pendingDoc = 0;
            int newest = 0;
            String dateString = "";

            if (draftItem.getFormValue().getDocument().getOptional().size() > 0) {
                for (int i = 0;i < draftItem.getFormValue().getDocument().getOptional().size();i++) {
                    if (!draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {
                        pendingDoc = pendingDoc + 1;
                    }

                    if (!draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {
                        String dateOne = draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date().split(" ")[0];
                        int date = Integer.valueOf(dateOne.split("-")[2]);
                        if (newest == 0) {
                            newest = date;
                        }

                        Log.d("Info", "Data "+date+" "+newest);

                        if (date > 0 && date < newest) {
                            newest = date;

                            dateString = chageDateFormat(draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date(), "due");
                        } else {
                            dateString = chageDateFormat(draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date(), "due");
                        }
                    }
                }
            }

            if (pendingDoc > 0) {
                draftViewHolder.txtDocumentInfo.setVisibility(View.VISIBLE);
                draftViewHolder.txtDocumentInfo.setText(""+pendingDoc+" promised doc, due "+dateString);
            }
        }

        draftViewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InquiryItem inquiryItem = listItems.get(position);
                /*Intent intent = new Intent(context, UploadDocumentActivity.class);
                intent.putExtra("data",inquiryItem);
                intent.putExtra("isUpdate",1);
                context.startActivity(intent);*/
                needActionFragment.showUploadPage(inquiryItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //Funtion to get adapter data from selected position
    public InquiryItem getItemAt(int position) {
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
