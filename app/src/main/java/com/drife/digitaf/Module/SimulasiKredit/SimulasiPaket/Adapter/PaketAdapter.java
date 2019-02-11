package com.drife.digitaf.Module.SimulasiKredit.SimulasiPaket.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiPaket.Activity.SimulasiPaketActivity;
import com.drife.digitaf.ORM.Database.PackageRule;
import com.drife.digitaf.UIModel.Paket;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;
import com.google.android.gms.common.util.SharedPreferencesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaketAdapter extends RecyclerView.Adapter<PaketAdapter.ViewHolder> {
    private List<Paket> data;
    private Context context;
    private List<PackageRule> packageRules;

    public PaketAdapter(Context context, List<Paket> data, List<PackageRule> packageRules) {
        this.data = data;
        this.context = context;
        this.packageRules = packageRules;
        ImageUtility.configureImageLoader(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_selector)
        ImageView ivSelector;
        @BindView(R.id.card_item)
        CardView cardItem;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.packageImage)
        ImageView packageImage;

        public ViewHolder(View itemVew) {
            super(itemVew);
            context = itemVew.getContext();
            ButterKnife.bind(this, itemView);
            setIsRecyclable(false);
        }

        public void bindHolder(final Paket paket, int position, final PackageRule packageRule) {

            boolean selected = paket.isSelected();

            if(paket.getId().equals(data.get(position).getId())){
                if(selected){
                    ivSelector.setVisibility(View.VISIBLE);
                }else{
                    ivSelector.setVisibility(View.GONE);
                }

                tvName.setText(paket.getText());
                tvDescription.setText(paket.getDescription());
                String image = paket.getImage();
                if(image != null && !image.equals("")){
                    ImageUtility.displayImage(paket.getImage(),packageImage);
                }
            }

            cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetSelected();
                    paket.setSelected(true);
                    data.set(getAdapterPosition(),paket);
                    //notifyDataSetChanged();
                    ((SimulasiPaketActivity)context).selectPackage(paket.getId());
                    ((SimulasiPaketActivity)context).setSelectedPackageRule(packageRule);
                    //((SimulasiPaketActivity)context).setCarList(paket.getCatagoryGroupId());
                    UserSession userSession = SharedPreferenceUtils.getUserSession(context);
                    if(userSession.getUser().getType().equals("so")){
                        ((SimulasiPaketActivity)context).setCarList_v2("");
                    }else {
                        ((SimulasiPaketActivity)context).setCarList_v2(userSession.getUser().getDealer().getBrand().getCode());
                    }
                    ((SimulasiPaketActivity)context).selectPackageCode(packageRule.getPackageCode());
                    Log.d("singo","nama paket : "+paket.getText());
                    Log.d("singo","package rule : "+packageRule.getPackageName());

                    notifyDataSetChanged();
                }
            });
            /*tv_title.setText(title);
            tv_quantity.setText(quantity);*/
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paket, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindHolder(data.get(position), position, packageRules.get(position));
    }

    public int getItemCount() {
        return data.size();
    }

    private void resetSelected(){
        for (int i=0; i<data.size(); i++){
            Paket paket = data.get(i);
            paket.setSelected(false);
            data.set(i,paket);
        }
    }

    /*private void select(pa){
        return packageRules.get()
    }*/
}

