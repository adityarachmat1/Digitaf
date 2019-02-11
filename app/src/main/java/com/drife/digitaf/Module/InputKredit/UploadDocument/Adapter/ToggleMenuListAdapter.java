package com.drife.digitaf.Module.InputKredit.UploadDocument.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.ToggleMenuItem;
import com.drife.digitaf.R;

import java.util.ArrayList;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class ToggleMenuListAdapter extends RecyclerView.Adapter {
    //Static variale to define layout tipe, is header or item

    private Context context;
    private ArrayList<ToggleMenuItem> listItems;

    private RecyclerView.LayoutParams originLayoutParam;

    public ToggleMenuListAdapter(Context context, ArrayList<ToggleMenuItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    //View holder for item layout
    public class ToggleMenuViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txtDueDate;
        public CheckBox cbChecklist;
        public RelativeLayout lyToggleMenu;

        public ToggleMenuViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDueDate = (TextView) itemView.findViewById(R.id.txtDueDate);
            cbChecklist = (CheckBox) itemView.findViewById(R.id.cbChecklist);
            lyToggleMenu = (RelativeLayout) itemView.findViewById(R.id.lyToggleMenu);
        }
    }

    //Function to define which layout to create/inflate
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_list_togglemenu_item, parent, false);
        ToggleMenuViewHolder holder = new ToggleMenuViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ToggleMenuItem toggleMenuItem = listItems.get(position);
        ToggleMenuViewHolder toggleMenuViewHolder = (ToggleMenuViewHolder) holder;

        if (toggleMenuItem.isVisible()) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 118));
            params.setMargins(27, 5, 27, 5);
            toggleMenuViewHolder.lyToggleMenu.setLayoutParams(params);
        } else if (!toggleMenuItem.isVisible()) {
            toggleMenuViewHolder.lyToggleMenu.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 0));
        }

        toggleMenuViewHolder.cbChecklist.setChecked(toggleMenuItem.isChecked());
        toggleMenuViewHolder.txtTitle.setText(toggleMenuItem.getTitle());
        toggleMenuViewHolder.txtDueDate.setText(toggleMenuItem.getDueDate());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //Funtion to get adapter data from selected position
    public ToggleMenuItem getItemAt(int position) {
        return this.listItems.get(position);
    }

    public int countCheckList() {
        int count = 0;

        for (int i = 0;i < getItemCount();i++) {
            if (getItemAt(i).isChecked() && getItemAt(i).isVisible()) {
                count = count + 1;
            }
        }

        return count;
    }

    public int countMenu() {
        int count = 0;

        for (int i = 0;i < getItemCount();i++) {
            if (getItemAt(i).isVisible()) {
                count = count + 1;
            }
        }

        return count;
    }

    public void setMenuVisible(int pos, boolean show) {
        getItemAt(pos).setVisible(show);
        notifyDataSetChanged();
    }

    public void setMenuChecked(int pos, boolean checked, String date) {
        getItemAt(pos).setChecked(checked);
        if (date.equals("")) {
            getItemAt(pos).setDueDate("-");
        } else {
            getItemAt(pos).setDueDate(TextUtility.changeDateFormat("dd/MM/yyyy", "dd MMM yyyy", date));
        }
        notifyDataSetChanged();
    }
}
