package com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.Adapter.AdapterADDB;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.Adapter.AdapterADDM;
import com.drife.digitaf.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableActivity extends AppCompatActivity{
    private String TAG = TableActivity.class.getSimpleName();

    @BindView(R.id.rv_onloan)
    RecyclerView rv_onloan;
    @BindView(R.id.rv_prepaid)
    RecyclerView rv_prepaid;

    private TableData tableData;
    private AdapterADDB adapter;
    private AdapterADDM adapterPrepaid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        tableData = (TableData) getIntent().getSerializableExtra("data");
        /*configure extended list*/
        rv_onloan.setNestedScrollingEnabled(false);
        rv_onloan.setLayoutManager(new LinearLayoutManager(this));
        rv_onloan.setHasFixedSize(true);

        rv_prepaid.setNestedScrollingEnabled(false);
        rv_prepaid.setLayoutManager(new LinearLayoutManager(this));
        rv_prepaid.setHasFixedSize(true);
    }

    private void initListeners(){

    }

    private void callFunctions(){
        setData();
    }

    /*private void setTable(){
        //set table title labels
        LegacyTableView.insertLegacyTitle("TDP", "ANGSURAN", "TENOR");
        //set table contents as string arrays
        LegacyTableView.insertLegacyContent("2999010", "John Deer", "50", "john@example.com",
                "332312", "Kennedy F", "33", "ken@example.com"
                ,"42343243", "Java Lover", "28", "Jlover@example.com"
                ,"4288383", "Mike Tee", "22", "miket@example.com");

        LegacyTableView legacyTableView = (LegacyTableView)findViewById(R.id.legacy_table_view);
        legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
        legacyTableView.setContent(LegacyTableView.readLegacyContent());

        //depending on the phone screen size default table scale is 100
        //you can change it using this method
        //legacyTableView.setInitialScale(100);//default initialScale is zero (0)

        //if you want a smaller table, change the padding setting
        legacyTableView.setTablePadding(7);

        //to enable users to zoom in and out:
        legacyTableView.setZoomEnabled(true);
        legacyTableView.setShowZoomControls(true);
        legacyTableView.setInitialScale(50);
        //remember to build your table as the last step
        legacyTableView.build();
    }*/

    private void setData(){
        List<Long> listTdpOnloan = tableData.getTDPOnloan();
        List<Long> listCicilanOnloan = tableData.getCicilanOnloan();
        List<Long> listTdpPrepaid = tableData.getTDPPrepaid();
        List<Long> listCicilanPrepaid = tableData.getCicilanPrepaid();
        List<Tenor> tenorList = tableData.getTenors();

        adapter = new AdapterADDB(getApplicationContext(),listTdpOnloan,listCicilanOnloan,tenorList, null,null,null,0);
        rv_onloan.setAdapter(adapter);
        adapterPrepaid = new AdapterADDM(getApplicationContext(),listTdpPrepaid,listCicilanPrepaid,tenorList, null,null,null,0);
        rv_prepaid.setAdapter(adapterPrepaid);
    }
}
