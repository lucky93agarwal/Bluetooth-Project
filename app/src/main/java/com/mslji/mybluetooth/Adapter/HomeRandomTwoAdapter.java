package com.mslji.mybluetooth.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mslji.mybluetooth.Model.ProductModeltwo;
import com.mslji.mybluetooth.R;
import com.mslji.mybluetooth.TestDetailsActivity;

import java.util.List;

public class HomeRandomTwoAdapter extends RecyclerView.Adapter<HomeRandomTwoAdapter.ViewHolder> {
    public static Context mcontext;

    public boolean run = false;
    public static List<ProductModeltwo> productList;
    public static boolean runtimer = true;
    public Context context;
    ViewHolder view;

    public ProductModeltwo getselfdata;
    String Contest = "";


    ViewHolder viewHolder;

    public HomeRandomTwoAdapter(Context context, List<ProductModeltwo> productList) {
        super();
        this.context = context;
        this.productList = productList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.horizontallayout, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        getselfdata = productList.get(i);
        view = viewHolder;



        viewHolder.tvname.setText(productList.get(i).getId()+"("+productList.get(i).getDate()+")");






    }

    @Override
    public int getItemCount() {


        return productList.size();


    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvname;

        public LinearLayout mlinear;


        public ViewHolder(final View itemView) {
            super(itemView);



            tvname = (TextView) itemView.findViewById(R.id.numtv);//
            mlinear = (LinearLayout) itemView.findViewById(R.id.linearone);//




            mlinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, TestDetailsActivity.class);
                    intent.putExtra("id",productList.get(getAdapterPosition()).getId());
                    intent.putExtra("date",productList.get(getAdapterPosition()).getDate());
                    intent.putExtra("num",productList.get(getAdapterPosition()).getNumbers());
                    mcontext.startActivity(intent);


                }
            });








//
//            etproductno = (EditText) itemView.findViewById(R.id.productno);
//            spscheme = (Spinner) itemView.findViewById(R.id.scheme);
//            spunit = (Spinner) itemView.findViewById(R.id.unit);
//            tvid = (TextView)itemView.findViewById(R.id.btnupdates);
//            tvcategoryId = (TextView)itemView.findViewById(R.id.btnsaves);
//            tvrupees = (TextView)itemView.findViewById(R.id.btnproductrupees);


//            imageView = itemView.findViewById(R.id.image);
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);


//            etproductno.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    productList.get(i).setCompleteMasalaSchemeData(spscheme.getSelectedItem().toString());
//                    productList.get(i).setCompleteMasalaUnitData(spunit.getSelectedItem().toString());
//                    int variable = i;
//                }
//            });
//            spscheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    productList.get(i).setCompleteMasalaSchemeDataPosition(spscheme.getSelectedItemPosition());
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });




//            etproductno.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
////                    getada.add(productList.get(i).getName());
//                    productList.get(i).setCompleteMasalaProducQuantity(charSequence.toString());
////
////                    productList.get(i).setCompleteMasalaSchemeData(spscheme.getSelectedItem().toString());
////                    productList.get(i).setCompleteMasalaUnitData(spunit.getSelectedItem().toString());
//                    productList.get(i).setCompleteMasalaProductPrice(Double.parseDouble(tvrupees.getText().toString()));
//                    productList.get(i).setCompleteMasalaProductName(tvproductname.getText().toString());
//
//                    productList.get(i).setProductId(tvid.getText().toString());
//                    productList.get(i).setCompleteMasalaCategoryId(tvcategoryId.getText().toString());
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                }
//            });



        }


    }

}




