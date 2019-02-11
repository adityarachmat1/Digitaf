package com.drife.digitaf.Module.FileChooser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.drife.digitaf.GeneralUtility.FileUtility.Option;
import com.drife.digitaf.Module.FileChooser.Activity.adapter.FileChooserAdapter;
import com.drife.digitaf.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileChooser extends AppCompatActivity {
    private String TAG = FileChooser.class.getSimpleName();

    @BindView(R.id.rcFileChooser)
    public RecyclerView rcFileChooser;
    @BindView(R.id.imgBack)
    public ImageView imgBack;

    private File currentDir;
    private FileChooserAdapter fileChooserAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chooser);
        ButterKnife.bind(this);

        initListeners();
        callFunctions();
    }

    private void initListeners() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callFunctions() {
        currentDir = new File("/sdcard/");
        fill(currentDir);
    }

    private void fill(File f) {
//        File[] dirs = f.listFiles();
//        List<Option> fls = new ArrayList<Option>();
//
//        try{
//            for(File ff: dirs) {
//                if(ff.isDirectory()) {
//                    File fileDir = new File(ff.getAbsolutePath());
//                    File[] fileDirs = fileDir.listFiles();
//
//                    for (File fileInfo: fileDirs) {
//                        if (!fileInfo.isDirectory() && fileInfo.getName().contains(".pdf")) {
//                            fls.add(new Option(fileInfo.getName(), "" + fileInfo.length(), fileInfo.getAbsolutePath()));
//                        }
//                    }
//                } else {
//                    fls.add(new Option(ff.getName(), "" + ff.length(), ff.getAbsolutePath()));
//                }
//            }
//        } catch(Exception e) {
//
//        }
//
//        Collections.sort(fls);

        File[]dirs = f.listFiles();
        this.setTitle("Current Dir: "+f.getName());
        List<Option>dir = new ArrayList<Option>();
        List<Option>fls = new ArrayList<Option>();
        try{
            for(File ff: dirs)
            {
                if(ff.isDirectory())
                    dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
                else
                {
                    if (!ff.isDirectory() && ff.getName().contains(".pdf")) {
                        fls.add(new Option(ff.getName(),""+ff.length(),ff.getAbsolutePath()));
                    }
                }
            }
        } catch(Exception e) {

        }

        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if(!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0,new Option("...","Direktori Sebelumnya",f.getParent()));

        fileChooserAdapter = new FileChooserAdapter(this, dir);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcFileChooser.setLayoutManager(layoutManager);
        rcFileChooser.setItemAnimator(new DefaultItemAnimator());
        rcFileChooser.setNestedScrollingEnabled(false);
        rcFileChooser.setAdapter(fileChooserAdapter);
    }

    public void onListItemClick(Option o) {
        if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("Direktori Sebelumnya")){
            currentDir = new File(o.getPath());
            fill(currentDir);
        } else {
            Intent intent = new Intent();
            intent.putExtra("pathPDF", o.getPath());
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
