package com.drife.digitaf.Module.OnBoardActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.drife.digitaf.Module.OnBoardActivity.Adapter.OnBoardAdapter;
import com.drife.digitaf.R;
import com.rd.PageIndicatorView;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardContainerActivity extends AppCompatActivity {
    private final String TAG = OnBoardContainerActivity.class.getSimpleName();

    public static int pos = 0;

    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    OnBoardAdapter onboardAdapter;

    private Long backPressed = 0L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.z_activity_onboard_container);
        ButterKnife.bind(this);

        callFunctions();
    }

    private void callFunctions() {
        onboardAdapter = new OnBoardAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(onboardAdapter);
        indicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                pos = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar.", Toast.LENGTH_SHORT).show();
            backPressed = System.currentTimeMillis();
        }
    }
}
