package com.mslji.mybluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.mslji.mybluetooth.Adapter.HomeRandomTwoAdapter;
import com.mslji.mybluetooth.Database.FirstTableData;
import com.mslji.mybluetooth.Database.MyDbHandler;
import com.mslji.mybluetooth.Database.Temp;
import com.mslji.mybluetooth.Model.ProductModeltwo;
import com.mslji.mybluetooth.R;

import java.util.ArrayList;
import java.util.List;

public class IDActivity extends AppCompatActivity {
    public ArrayList<String> itemsid = new ArrayList<String>();
    public ArrayList<String> items_num = new ArrayList<String>();
    public ArrayList<String> items_date = new ArrayList<String>();


    public List<ProductModeltwo> productModelsthree = new ArrayList<>();
    public ProductModeltwo getproductmodelthree;
    public RecyclerView mRecyclerViewsec;
    public RecyclerView.LayoutManager mLayoutManagersecthree;
    public HomeRandomTwoAdapter adaptersecthree;

    public MyDbHandler myDbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_d);

        myDbHandler = new MyDbHandler(getApplicationContext(),"userbd",null,1);

        Temp.setMyDbHandler(myDbHandler);

        mRecyclerViewsec = (RecyclerView) findViewById(R.id.recycler_view_myteam_two);
        itemsid =  (ArrayList<String>) getIntent().getSerializableExtra("id");

        items_num =  (ArrayList<String>) getIntent().getSerializableExtra("num");
        items_date =  (ArrayList<String>) getIntent().getSerializableExtra("date");



        for (int i = 0; i < itemsid.size(); i++) {
            FirstTableData user = new FirstTableData();
                              user.setPasentid(itemsid.get(i));
                                user.setPasentnum(items_num.get(i));
                                user.setPasentdate(items_date.get(i));
                                user.setAlldata("");
                                int h = myDbHandler.insertUser(user);
                                Log.d("WalletLuckyYUYnew", "inseart check = " + h);

        }











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

        adaptersecthree = new HomeRandomTwoAdapter(IDActivity.this, productModelsthree);
        final GridLayoutManager layoutManager = new GridLayoutManager(IDActivity.this, 3);
        mLayoutManagersecthree = new LinearLayoutManager(IDActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewsec.setLayoutManager(layoutManager);
        mRecyclerViewsec.setAdapter(adaptersecthree);
        adaptersecthree.notifyDataSetChanged();
        Log.d("WalletLuckyYUYnew","between new new new page id size = =  "+itemsid.toString());
        Log.d("WalletLuckyYUYnew","between new new new page num size = =  "+items_num.toString());
        Log.d("WalletLuckyYUYnew","between new new new page date size = =  "+items_date.toString());
    }
}