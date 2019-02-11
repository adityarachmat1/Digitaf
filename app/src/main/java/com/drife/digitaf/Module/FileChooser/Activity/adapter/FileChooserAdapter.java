package com.drife.digitaf.Module.FileChooser.Activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.FileUtility.Option;
import com.drife.digitaf.Module.FileChooser.Activity.FileChooser;
import com.drife.digitaf.R;

import java.text.DecimalFormat;
import java.util.List;

public class FileChooserAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Option> listItems;

    public FileChooserAdapter(Context context, List<Option> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    //View holder for item layout
    public class DraftViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFileName, txtFileSize, txtFileAddedDate;
        public ImageView imgIcon;

        public DraftViewHolder(View itemView) {
            super(itemView);

            txtFileName = (TextView) itemView.findViewById(R.id.txtFileName);
            txtFileSize = (TextView) itemView.findViewById(R.id.txtFileSize);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
        }
    }

    //Function to define which layout to create/inflate
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items_filechooser, parent, false);
        return new FileChooserAdapter.DraftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Option optionItem = getItemAt(position);

        DraftViewHolder fileChooserHolder = (DraftViewHolder) holder;

        fileChooserHolder.txtFileName.setText(optionItem.getName());
        fileChooserHolder.txtFileSize.setText(optionItem.getData().equalsIgnoreCase("folder") || optionItem.getData().equalsIgnoreCase("Direktori Sebelumnya") ? optionItem.getData() : getFileSize(Long.parseLong(optionItem.getData())));

        if (optionItem.getData().equalsIgnoreCase("folder") || optionItem.getData().equalsIgnoreCase("Direktori Sebelumnya")) {
            fileChooserHolder.imgIcon.setImageResource(R.drawable.ic_folder_filechooser);
        } else {
            fileChooserHolder.imgIcon.setImageResource(R.drawable.ic_file_pdf);
        }

        fileChooserHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("pathPDF", getItemAt(position).getPath());
//                ((Activity)context).setResult(Activity.RESULT_OK,intent);
//                ((Activity)context).finish();

                ((FileChooser)context).onListItemClick(optionItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //Funtion to get adapter data from selected position
    public Option getItemAt(int position) {
        return this.listItems.get(position);
    }

    public void clear() {
        listItems.clear();
        notifyDataSetChanged();
    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
