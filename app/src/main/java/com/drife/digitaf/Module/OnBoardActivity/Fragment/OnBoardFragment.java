package com.drife.digitaf.Module.OnBoardActivity.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Loading.LoadingActivity;
import com.drife.digitaf.Module.Login.Activity.LoginActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.OnBoardActivity.OnBoardContainerActivity;
import com.drife.digitaf.Module.SplashScreen.Activity.SplashScreenActivity;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardFragment extends Fragment {
    private final String TAG = OnBoardFragment.class.getSimpleName();

    public int pos = 0;
    private View rootView;

    @BindView(R.id.imgClose)
    ImageView imgClose;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgOnboard)
    ImageView imgOnboard;
    @BindView(R.id.txtContent)
    TextView txtContent;
    @BindView(R.id.txtSkip)
    TextView txtSkip;
    @BindView(R.id.btnTest)
    Button btnTest;

    private Drawable[] listImages;
    private String[] listTitles;
    private String[] listContents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.z_onboard_view, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVariables();
        initListeners();
        callFunctions();
    }

    private void initListeners() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkSession();
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != 2) {
                    ((OnBoardContainerActivity)getActivity()).viewPager.setCurrentItem(pos+1);
                } else {
                    checkSession();
                }
            }
        });

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSession();
            }
        });
    }

    private void initVariables() {
        listImages = new Drawable[]{getResources().getDrawable(R.drawable.ic_onboard_1), getResources().getDrawable(R.drawable.ic_onboard_2), getResources().getDrawable(R.drawable.ic_onboard_3)};
        listTitles = new String[]{getActivity().getResources().getString(R.string.text_onboard_title1), getActivity().getResources().getString(R.string.text_onboard_title2), getActivity().getResources().getString(R.string.text_onboard_title3)};
        listContents = new String[]{getActivity().getResources().getString(R.string.text_onboard_content1), getActivity().getResources().getString(R.string.text_onboard_content2), getActivity().getResources().getString(R.string.text_onboard_content3)};

        txtSkip.setText(Html.fromHtml("<u>Skip</u>"));
    }

    private void callFunctions() {
        for (int i = 0;i < listTitles.length;i++) {
            if (pos == i) {
                imgOnboard.setImageDrawable(listImages[i]);
                txtTitle.setText(listTitles[pos]);
                txtContent.setText(listContents[pos]);
            }
        }

        if (pos == 2) {
            btnTest.setText(getActivity().getResources().getString(R.string.btn_onboard_mulai));
            txtSkip.setVisibility(View.GONE);
        }
    }

    private void checkSession() {
//        UserSession userSession = SharedPreferenceUtils.getUserSession(getActivity());
//        if(userSession != null){
//            Intent intent = new Intent(getActivity(), Main.class);
//            startActivity(intent);
//            getActivity().finish();
//        }else{
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        }

        SharedPreferences preferences = getActivity().getSharedPreferences("sync",getActivity().MODE_PRIVATE);
        String stat = preferences.getString("sync", "");

        if(stat.equals("") || stat.equals("0")){
            Intent intent = new Intent(getActivity(), LoadingActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }else{
            Intent intent = new Intent(getActivity(), Main.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }
}
