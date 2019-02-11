package com.drife.digitaf.Module.InputKredit.UploadDocument.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.drife.digitaf.Module.InputKredit.UploadDocument.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.UploadDocumentItem;
import com.drife.digitaf.R;

import java.util.ArrayList;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class UploadDocumentListAdapter extends RecyclerView.Adapter {
    //Static variale to define layout tipe, is header or item

    private Context context;
    private ArrayList<UploadDocumentItem> listItems;

    public UploadDocumentListAdapter(Context context, ArrayList<UploadDocumentItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    //View holder for item layout
    public class UploadDocumentViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitleInfo, txtTitleCantumkanFoto, txtDocumentInfo;
        public RelativeLayout btnAddAttachment, btnPreview;
        public ImageView ivIcon;
        public CardView lyItem;
        public View parentView;

        public UploadDocumentViewHolder(View itemView) {
            super(itemView);

            txtTitleInfo = (TextView) itemView.findViewById(R.id.txtTitleInfo);
            txtTitleCantumkanFoto = (TextView) itemView.findViewById(R.id.txtTitleCantumkanFoto);
            txtDocumentInfo = (TextView) itemView.findViewById(R.id.txtDocumentInfo);
            btnAddAttachment = (RelativeLayout) itemView.findViewById(R.id.btnAddAttachment);
            btnPreview = (RelativeLayout) itemView.findViewById(R.id.btnPreview);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            lyItem = itemView.findViewById(R.id.lyItem);
            parentView = itemView;
            setIsRecyclable(false);
        }
    }

    //View holder for item layout
    public class InfoDocumentViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitleInfo;
        public Switch switchInfo;

        public InfoDocumentViewHolder(View itemView) {
            super(itemView);

            txtTitleInfo = (TextView) itemView.findViewById(R.id.txtTitleInfo);
            switchInfo = itemView.findViewById(R.id.switchInfo);
            setIsRecyclable(false);
        }
    }

    //Function to define which layout to create/inflate
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType) {
            case UploadDocumentItem.TYPE_DOCUMENT:
                itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.r_list_uploaddocument_item, parent, false);
                return new UploadDocumentViewHolder(itemView);
            case UploadDocumentItem.TYPE_INFO:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.r_list_uploaddocument_1_item, parent, false);
                return new InfoDocumentViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final UploadDocumentItem uploadDocumentItem = listItems.get(position);

        if (getItemViewType(position) == UploadDocumentItem.TYPE_DOCUMENT) {
            UploadDocumentViewHolder uploadDocumentViewHolder = (UploadDocumentViewHolder) holder;

            if(uploadDocumentItem.isEnable()){
                uploadDocumentViewHolder.lyItem.setVisibility(View.VISIBLE);

                uploadDocumentViewHolder.txtTitleInfo.setText(uploadDocumentItem.getTitleDocument());
                if(uploadDocumentItem.getDocumentType().toString().equals(UploadDocumentItem.DocumentType.KTP.toString())){
                    uploadDocumentViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ktp_128));
                }else if(uploadDocumentItem.getDocumentType().toString().equals(UploadDocumentItem.DocumentType.SPK.toString())){
                    uploadDocumentViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_spk_128));
                }else if(uploadDocumentItem.getDocumentType().toString().equals(UploadDocumentItem.DocumentType.KartuNama.toString())){
                    uploadDocumentViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_kartunama_128));
                }else if(uploadDocumentItem.getDocumentType().toString().equals(UploadDocumentItem.DocumentType.KK.toString())){
                    uploadDocumentViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_kk_128));
                }else if(uploadDocumentItem.getDocumentType().toString().equals(UploadDocumentItem.DocumentType.BuktiKeuangan.toString())){
                    uploadDocumentViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_buktikeu_128));
                }else if(uploadDocumentItem.getDocumentType().toString().equals(UploadDocumentItem.DocumentType.NPWP.toString())){
                    uploadDocumentViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_npwp_128));
                }else if(uploadDocumentItem.getDocumentType().toString().equals(UploadDocumentItem.DocumentType.KTPIstriSuami.toString())){
                    uploadDocumentViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ktp_128));
                }

                uploadDocumentViewHolder.btnAddAttachment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NestedScrollView rootView = (NestedScrollView) ((Activity)context).findViewById(R.id.nestedMinimum);
                    /*PopupUtils.inputKTP(((Activity)context), rootView, new PopupUtils.SaveDataListener() {
                        @Override
                        public void isSaved(boolean isSaved, HashMap<String, String> datas) {

                        }
                    });*/

                        ((UploadDocumentActivity)context).showPopupWindow(uploadDocumentItem.getDocumentType());
                    }
                });

            }else{
                //uploadDocumentViewHolder.lyItem.setVisibility(View.GONE);
                uploadDocumentViewHolder.parentView.setVisibility(View.GONE);
                uploadDocumentViewHolder.parentView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }

        } else if (getItemViewType(position) == UploadDocumentItem.TYPE_INFO) {
            InfoDocumentViewHolder infoDocumentViewHolder = (InfoDocumentViewHolder) holder;
            infoDocumentViewHolder.txtTitleInfo.setText(uploadDocumentItem.getTitleDocument());

            if(uploadDocumentItem.getDocumentType().toString().equals(UploadDocumentItem.DocumentType.KTPIstriSuami.toString())){
                infoDocumentViewHolder.switchInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        UploadDocumentItem documentItem = listItems.get(position+1);
                        if(isChecked){
                            documentItem.setEnable(true);
                        }else{
                            documentItem.setEnable(false);
                        }
                        listItems.set(holder.getAdapterPosition(),documentItem);
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //Funtion to get adapter data from selected position
    public UploadDocumentItem getItemAt(int position) {
        return this.listItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        //Detect which type of view
        int type = listItems.get(position).getViewType();

        return type;
    }
}
