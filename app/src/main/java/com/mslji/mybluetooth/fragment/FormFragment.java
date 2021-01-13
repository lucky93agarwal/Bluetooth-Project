package com.mslji.mybluetooth.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mslji.mybluetooth.Database.RefUrl;
import com.mslji.mybluetooth.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FormFragment extends Fragment {

    String reserver = "";
    //// progress bar
    public AVLoadingIndicatorView progressbar;

    public EditText etcerebo_scan_date,etcerebo_scan_time,etrecord_id,ethospital_patient_id,etpatient_name, etageet, etif_yes_et, etany_othere_et;

    public EditText etif_no_where_did_you_go_first,etdate_of_symptoms,  etgcs_score, etany_other_remarks_et, etblood_pressure_et;



    public RadioGroup radioSexGroup, radioSkinColorGroup, radioHairdensityGroup, radiomethod_of_reaching_hospitalrgGroup, radiotype_of_accidentrbtnGroup,
            radiogeographical_location_of_accidentGroup, radiopart_of_head_that_was_affectedGroup,  radiohospital_after_injuryGroup,
            radiosymptomsGroup,  radiogcs_report_uploaded_rgbtnGroup, radioct_scan_uploadedbtnGroup,  radiois_a_high_blood_pressure_patientbtnGroup;

    public RadioButton radioSexButton,radioSkinColorButton,radioHairdensityButton,radiomethod_of_reaching_hospitalrgButton, radiotype_of_accidentrbtnButton,
            radiogeographical_location_of_accidentButton, radiopart_of_head_that_was_affectedButton,  radiohospital_after_injuryButton,
            radiosymptomsButton,  radiogcs_report_uploaded_rgbtnButton, radioct_scan_uploadedbtnButton,  radiois_a_high_blood_pressure_patientbtnButton;

    public AppCompatButton btnSubmit;


    public String number,id,date;

    public FormFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view;
        view =  inflater.inflate(R.layout.fragment_form, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("datanew",MODE_PRIVATE);
        id= sharedPreferences.getString("id","");
        date= sharedPreferences.getString("date","");

        // progress bar
        progressbar = (AVLoadingIndicatorView)view.findViewById(R.id.avi);
        etif_no_where_did_you_go_first = view.findViewById(R.id.etif_no_where_did_you_go_firstet);
        etdate_of_symptoms = view.findViewById(R.id.etdate_of_symptomset);



        etgcs_score = view.findViewById(R.id.etgcs_scoreet);
        etany_other_remarks_et = view.findViewById(R.id.any_other_remarks_et);

        etblood_pressure_et = view.findViewById(R.id.blood_pressure_et);

        radiogcs_report_uploaded_rgbtnGroup=(RadioGroup)view.findViewById(R.id.gcs_report_uploaded_rgbtn);


        radioct_scan_uploadedbtnGroup=(RadioGroup)view.findViewById(R.id.ct_scan_uploadedbtn);


        radioSexGroup=(RadioGroup)view.findViewById(R.id.radioSex);
        radioSkinColorGroup=(RadioGroup)view.findViewById(R.id.skincolorrg);
        radioHairdensityGroup=(RadioGroup)view.findViewById(R.id.hairdensity);

        radiomethod_of_reaching_hospitalrgGroup=(RadioGroup)view.findViewById(R.id.method_of_reaching_hospitalrg);
        radiotype_of_accidentrbtnGroup=(RadioGroup)view.findViewById(R.id.type_of_accidentrbtn);

        radiogeographical_location_of_accidentGroup=(RadioGroup)view.findViewById(R.id.geographical_location_of_accidentrbtn);

        radiopart_of_head_that_was_affectedGroup=(RadioGroup)view.findViewById(R.id.part_of_head_that_was_affectedrbtn);

        radiohospital_after_injuryGroup=(RadioGroup)view.findViewById(R.id.hospital_after_injuryrbtn);


        radiosymptomsGroup=(RadioGroup)view.findViewById(R.id.symptomsrbtn);


        radiois_a_high_blood_pressure_patientbtnGroup=(RadioGroup)view.findViewById(R.id.is_a_high_blood_pressure_patientbtn);



        etany_othere_et = (EditText)view.findViewById(R.id.any_othere_et);


        etif_yes_et = (EditText)view.findViewById(R.id.if_yes_et);

        etageet = (EditText)view.findViewById(R.id.ageet);
        btnSubmit=(AppCompatButton)view.findViewById(R.id.button);
        etcerebo_scan_date = view.findViewById(R.id.cerebo_scan_dateet);
        etcerebo_scan_date.setText(date);
        etcerebo_scan_date.setEnabled(false);
        etcerebo_scan_time = view.findViewById(R.id.cerebo_scan_dateetet);
        etrecord_id = view.findViewById(R.id.record_idet);
        etrecord_id.setText(id);
        etrecord_id.setEnabled(false);
        ethospital_patient_id = view.findViewById(R.id.hospital_patient_idet);
        etpatient_name = view.findViewById(R.id.patient_nameet);


        btnSubmit=(AppCompatButton)view.findViewById(R.id.button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId=radioSexGroup.getCheckedRadioButtonId();
                radioSexButton=(RadioButton)view.findViewById(selectedId);


                int selectedSkinColorId=radioSkinColorGroup.getCheckedRadioButtonId();
                radioSkinColorButton=(RadioButton)view.findViewById(selectedSkinColorId);



                int selectedHairdensityId=radioHairdensityGroup.getCheckedRadioButtonId();
                radioHairdensityButton=(RadioButton)view.findViewById(selectedHairdensityId);


                int selectedmethod_of_reaching_hospitalrgId=radiomethod_of_reaching_hospitalrgGroup.getCheckedRadioButtonId();
                radiomethod_of_reaching_hospitalrgButton=(RadioButton)view.findViewById(selectedmethod_of_reaching_hospitalrgId);


                int selectedtype_of_accidentrId=radiotype_of_accidentrbtnGroup.getCheckedRadioButtonId();
                radiotype_of_accidentrbtnButton=(RadioButton)view.findViewById(selectedtype_of_accidentrId);




                int selectedgeographical_location_of_accidentId=radiogeographical_location_of_accidentGroup.getCheckedRadioButtonId();
                radiogeographical_location_of_accidentButton=(RadioButton)view.findViewById(selectedgeographical_location_of_accidentId);


                int selectedpart_of_head_that_was_affectedId=radiopart_of_head_that_was_affectedGroup.getCheckedRadioButtonId();
                radiopart_of_head_that_was_affectedButton=(RadioButton)view.findViewById(selectedpart_of_head_that_was_affectedId);


                int selectedradiohospital_after_injuryGroupId=radiohospital_after_injuryGroup.getCheckedRadioButtonId();
                radiohospital_after_injuryButton=(RadioButton)view.findViewById(selectedradiohospital_after_injuryGroupId);



                int selectedsymptomsId=radiosymptomsGroup.getCheckedRadioButtonId();
                radiosymptomsButton=(RadioButton)view.findViewById(selectedsymptomsId);



                int selectedradiogcs_report_uploaded_rgbtnGroupId=radiogcs_report_uploaded_rgbtnGroup.getCheckedRadioButtonId();
                radiogcs_report_uploaded_rgbtnButton=(RadioButton)view.findViewById(selectedradiogcs_report_uploaded_rgbtnGroupId);



                int selectedradioct_scan_uploadedbtnGroupId=radioct_scan_uploadedbtnGroup.getCheckedRadioButtonId();
                radioct_scan_uploadedbtnButton=(RadioButton)view.findViewById(selectedradioct_scan_uploadedbtnGroupId);



                int selectedradiois_a_high_blood_pressure_patientbtnGroupId=radiois_a_high_blood_pressure_patientbtnGroup.getCheckedRadioButtonId();
                radiois_a_high_blood_pressure_patientbtnButton=(RadioButton)view.findViewById(selectedradiois_a_high_blood_pressure_patientbtnGroupId);

                Toast.makeText(getActivity(),radiois_a_high_blood_pressure_patientbtnButton.getText(), Toast.LENGTH_SHORT).show();
                Login login = new Login();
                login.execute();
            }
        });

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class Login extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
//            pd = ProgressDialog.show(EnterMobileActivity.this, "", "Please Wait...");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                String Scan_date=etcerebo_scan_date.getText().toString();
                String Scan_time = etcerebo_scan_time.getText().toString();
//                String Record_id = etrecord_id.getText().toStirng();
                String password="etpassword.getText().toString()";
                String riferalID = "sharedPreferences.getString(";

                Log.d("CheckDatariferal","Mobile = "+Scan_date);
                Log.d("CheckDatariferal","Password = "+password);
                Log.d("CheckDatariferal","riferalID = "+riferalID);
                @SuppressLint("WrongThread")
                HttpPost httpPost = new HttpPost(RefUrl.BaseUrl);
                JSONObject postData = new JSONObject();
                postData.put("cdblock","Login");
                postData.put("mobile",Scan_date);
                postData.put("password",password);
                postData.put("riferalID",riferalID);

                // Toast.makeText(getApplicationContext(),js.toString(),Toast.LENGTH_SHORT).show();
                ArrayList<BasicNameValuePair> b1 = new ArrayList<BasicNameValuePair>();
                b1.add(new BasicNameValuePair("req", postData.toString()));

                httpPost.setEntity((HttpEntity) new UrlEncodedFormEntity(b1));
                DefaultHttpClient client = new DefaultHttpClient();
                HttpResponse resp = client.execute((HttpUriRequest) httpPost);

                BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                String line = "";
                if ((line = br.readLine()) != null) {
                    reserver = line;
                    Toast.makeText(getActivity(), reserver.toString(), Toast.LENGTH_LONG).show();
                }
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                JSONObject jsonObject = new JSONObject(reserver);
                JSONArray response = jsonObject.getJSONArray("response");
                String res_result = response.getJSONObject(0).getString("result");



                Log.d("CheckData","reserver = "+reserver);
                Log.d("CheckData","jsonObject = "+jsonObject);




                if(res_result.equalsIgnoreCase("Success")){



                }else {
                    Toast.makeText(getActivity(), "Mobile Number not register", Toast.LENGTH_LONG).show();


                }
                progressbar.setVisibility(View.GONE);

                //Toast.makeText(getApplicationContext(),reserver,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}