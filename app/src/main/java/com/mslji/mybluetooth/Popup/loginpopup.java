package com.mslji.mybluetooth.Popup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mslji.mybluetooth.DeviceScanActivity;
import com.mslji.mybluetooth.R;
import com.mslji.mybluetooth.SendActivity;

public class loginpopup {
    public Dialog epicDialog;
    private final Context _context;
    public EditText etmsg;
    public Button btnsubmit;
    public String Studentid;
    public String Mssage,Tutorid;


    public loginpopup(Context context) {
        this._context = context;

        epicDialog = new Dialog(context); // for popup Dialog
    }


    public void addpopup(final String tutor_id,final String name) {
        epicDialog.setContentView(R.layout.messagelayout);

        etmsg = (EditText) epicDialog.findViewById(R.id.messagete);

        btnsubmit =(Button) epicDialog.findViewById(R.id.sendbtn);


//        btnsubmit.setOnClickListener(View.OnClickListener);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(etmsg.getText().length()==0){
//                    Toast.makeText(_context, "Enter your Message", Toast.LENGTH_SHORT).show();
//                }else if(etmsg.getText().toString().equalsIgnoreCase(tutor_id)){
                Intent intent = new Intent(_context, DeviceScanActivity.class);
                intent.putExtra(SendActivity.EXTRAS_DEVICE_NAME, name);
//                intent.putExtra(SendActivity.EXTRAS_DEVICE_ADDRESS, etmsg.getText().toString().trim());
                intent.putExtra(SendActivity.EXTRAS_DEVICE_ADDRESS, "68:27:19:3C:7F:3D");
                _context.startActivity(intent);
//                }else {
//                    Toast.makeText(_context, "Wrong Credencial", Toast.LENGTH_SHORT).show();
//                }
            }
        });



        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // popup background transparent
        epicDialog.show();
    }
}
