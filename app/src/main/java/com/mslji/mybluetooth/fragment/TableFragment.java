package com.mslji.mybluetooth.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mslji.mybluetooth.R;

import static android.content.Context.MODE_PRIVATE;


public class TableFragment extends Fragment {



    public TableFragment() {
        // Required empty public constructor
    }

    public TextView tvleft_frontal, tvright_frontal;
    public TextView tvleft_par, tvright_par;


    public TextView tvleft_temp, tvright_temp;


    public TextView tvleft_occ, tvright_occ;
    public TextView tvid, tvdate;
    public String number,id,date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view;
        view =  inflater.inflate(R.layout.fragment_table, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("datanew",MODE_PRIVATE);
        number= sharedPreferences.getString("num","");
        id= sharedPreferences.getString("id","");
        date= sharedPreferences.getString("date","");


        tvid = (TextView)view.findViewById(R.id.idtv);
        tvdate = (TextView)view.findViewById(R.id.datetv);

        tvid.setText(id);
        tvdate.setText(date);

        Log.d("karnikalucky","number =  "+number);
        Log.d("karnikalucky","ID =  "+id);
        Log.d("karnikalucky","DATE =  "+date);
        Log.d("karnikalucky","number 0 =  "+number.charAt(0));
        Log.d("karnikalucky","number 1 =  "+number.charAt(1));





        tvleft_frontal = (TextView)view.findViewById(R.id.left_f_tv);
        tvleft_frontal.setText(String.valueOf(number.charAt(0)));
        tvright_frontal = (TextView)view.findViewById(R.id.right_f_tv);
        tvright_frontal.setText(String.valueOf(number.charAt(1)));

        tvleft_temp = (TextView)view.findViewById(R.id.left_t_tv);
        tvleft_temp.setText(String.valueOf(number.charAt(2)));
        tvright_temp = (TextView)view.findViewById(R.id.right_t_tv);
        tvright_temp.setText(String.valueOf(number.charAt(3)));


        tvleft_par = (TextView)view.findViewById(R.id.left_p_tv);
        tvleft_par.setText(String.valueOf(number.charAt(4)));
        tvright_par = (TextView)view.findViewById(R.id.right_p_tv);
        tvright_par.setText(String.valueOf(number.charAt(5)));



        tvleft_occ = (TextView)view.findViewById(R.id.left_o_tv);
        tvleft_occ.setText(String.valueOf(number.charAt(6)));
        tvright_occ = (TextView)view.findViewById(R.id.right_o_tv);
        tvright_occ.setText(String.valueOf(number.charAt(7)));


        return view;
    }
}