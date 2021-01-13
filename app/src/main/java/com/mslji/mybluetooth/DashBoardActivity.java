package com.mslji.mybluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mslji.mybluetooth.Adapter.HomeRandomTwoAdapter;
import com.mslji.mybluetooth.Database.FirstTableData;
import com.mslji.mybluetooth.Database.MyDbHandler;
import com.mslji.mybluetooth.Database.Temp;
import com.mslji.mybluetooth.Model.ProductModeltwo;
import com.mslji.mybluetooth.R;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {
    public MyDbHandler myDbHandler;


    public List<ProductModeltwo> productModelsthree = new ArrayList<>();
    public ProductModeltwo getproductmodelthree;
    public RecyclerView mRecyclerViewsec;
    public RecyclerView.LayoutManager mLayoutManagersecthree;
    public HomeRandomTwoAdapter adaptersecthree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_d);


        myDbHandler = new MyDbHandler(getApplicationContext(),"userbd",null,1);

        Temp.setMyDbHandler(myDbHandler);

        mRecyclerViewsec = (RecyclerView) findViewById(R.id.recycler_view_myteam_two);


        MyDbHandler myDbHandler = Temp.getMyDbHandler();
        ArrayList<FirstTableData> arrayList = myDbHandler.viewUser();


        if (productModelsthree.size() > 0) {
            productModelsthree.clear();
        }
        for (FirstTableData u:arrayList){


            getproductmodelthree = new ProductModeltwo();
            getproductmodelthree.setDate(u.getPasentdate());
            getproductmodelthree.setId(u.getPasentid());
            getproductmodelthree.setNumbers(u.getPasentdate());

            productModelsthree.add(getproductmodelthree);

        }


        adaptersecthree = new HomeRandomTwoAdapter(DashBoardActivity.this, productModelsthree);
        final GridLayoutManager layoutManager = new GridLayoutManager(DashBoardActivity.this, 3);
        mLayoutManagersecthree = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewsec.setLayoutManager(layoutManager);
        mRecyclerViewsec.setAdapter(adaptersecthree);
        adaptersecthree.notifyDataSetChanged();
    }
}