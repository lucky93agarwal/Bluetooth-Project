package com.mslji.mybluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mslji.mybluetooth.Database.FirstTableData;
import com.mslji.mybluetooth.Database.MyDbHandler;
import com.mslji.mybluetooth.Database.Temp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SendActivity extends AppCompatActivity {
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    private static final String MLDP_CONTROL_PRIVATE_CHAR = "00035b03-58e6-07dd-021a-08123a0003ff";
    public static final String MLDP_DATA_PRIVATE_CHAR = "00035b03-58e6-07dd-021a-08123a000301";
    private static final String MLDP_PRIVATE_SERVICE = "00035b03-58e6-07dd-021a-08123a000300";
    /* access modifiers changed from: private */
    public static final String TAG = SendActivity.class.getSimpleName();
    /* access modifiers changed from: private */
    public SharedPreferences sharedPreferenceslucky;
    public SharedPreferences.Editor editnew;
    public BluetoothService mBluetoothLeService;
    private Button mClearIncomingButton;
    private final View.OnClickListener mClearIncomingButtonListener = new View.OnClickListener() {
        public void onClick(View view) {
            SendActivity.this.mIncomingText.setText((CharSequence) null);
        }
    };
    private Button mClearOutgoingButton;
    private final View.OnClickListener mClearOutgoingButtonListener = new View.OnClickListener() {
        public void onClick(View view) {
            SendActivity.this.mOutgoingText.setText((CharSequence) null);
        }
    };
    /* access modifiers changed from: private */
    public boolean mConnected = false;
    /* access modifiers changed from: private */
    public TextView mConnectionState;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic mDataMDLP;
    /* access modifiers changed from: private */
    public String mDeviceAddress;
    private String mDeviceName;
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.d(SendActivity.TAG, " BroadcastReceiver.onReceive ACTION_GATT_CONNECTED");
//                Log.d("WalletLUcky","1");
                boolean unused = SendActivity.this.mConnected = true;
                SendActivity.this.updateConnectionState(R.string.connected);
                SendActivity.this.invalidateOptionsMenu();

            } else if (BluetoothService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Log.d(SendActivity.TAG, " BroadcastReceiver.onReceive ACTION_GATT_DISCONNECTED");
//                Log.d("WalletLUcky","2 ");
                boolean unused2 = SendActivity.this.mConnected = false;
                SendActivity.this.updateConnectionState(R.string.disconnected);
                SendActivity.this.invalidateOptionsMenu();
                SendActivity.this.clearUI();
            } else if (BluetoothService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                Log.d(SendActivity.TAG, " BroadcastReceiver.onReceive ACTION_GATT_SERVICES_DISCOVERED");
//                Log.d("WalletLUcky","3");
                SendActivity.this.findMldpGattService(SendActivity.this.mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothService.ACTION_DATA_AVAILABLE.equals(action)) {
                Log.d(SendActivity.TAG, " BroadcastReceiver.onReceive ACTION_DATA_AVAILABLE");
//                Log.d("WalletLUcky","4");

                Log.d("WalletLUckyLucky","appendData == "+intent.getStringExtra(BluetoothService.EXTRA_DATA));
                appendData(intent.getStringExtra(BluetoothService.EXTRA_DATA));
            } else if (BluetoothService.ACTION_DATA_WRITTEN.equals(action)) {
                Log.d(SendActivity.TAG, " BroadcastReceiver.onReceive ACTION_DATA_WRITTEN");
//                Log.d("WalletLUcky","5");
            }
        }
    };



    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive (BluetoothSocket socket)
        {
            bluetoothSocket=socket;
            InputStream tempIn=null;
            OutputStream tempOut=null;

            try {
                tempIn=bluetoothSocket.getInputStream();
                tempOut=bluetoothSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream=tempIn;
            outputStream=tempOut;
        }

        public void run()
        {
            byte[] buffer=new byte[1024];
            int bytes;

            while (true)
            {
                try {
                    bytes=inputStream.read(buffer);
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING=2;
    static final int STATE_CONNECTED=3;
    static final int STATE_CONNECTION_FAILED=4;
    static final int STATE_MESSAGE_RECEIVED=5;
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what)
            {
                case STATE_LISTENING:

                    break;
                case STATE_CONNECTING:

                    break;
                case STATE_CONNECTED:

                    break;
                case STATE_CONNECTION_FAILED:

                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff= (byte[]) msg.obj;
                    String tempMsg=new String(readBuff,0,msg.arg1);
//                    msg_box.setText(tempMsg);
                    mIncomingText.append(tempMsg);
                    break;
            }
            return true;
        }
    });
    /* access modifiers changed from: private */
    public TextView mIncomingText;
    /* access modifiers changed from: private */
    public EditText mOutgoingText;
    private final TextWatcher mOutgoingTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence cs, int start, int before, int count) {
            if (count > before) {
//
                Log.d("WalletLuckyYUY","cs = "+cs.subSequence(start + before, start + count).toString());
                mDataMDLP.setValue(cs.subSequence(start + before, start + count).toString());
                mBluetoothLeService.writeCharacteristic(mDataMDLP);
            }
        }

        public void afterTextChanged(Editable edtbl) {
        }
    };
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            BluetoothService unused = SendActivity.this.mBluetoothLeService = ((BluetoothService.LocalBinder) service).getService();
            if (!SendActivity.this.mBluetoothLeService.initialize()) {
                Log.e(SendActivity.TAG, "Unable to initialize Bluetooth");
                SendActivity.this.finish();
            }
            SendActivity.this.mBluetoothLeService.connect(SendActivity.this.mDeviceAddress);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            BluetoothService unused = SendActivity.this.mBluetoothLeService = null;
        }
    };
    public MyDbHandler myDbHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Intent intent = getIntent();

        myDbHandler = new MyDbHandler(getApplicationContext(),"userbd",null,1);

        Temp.setMyDbHandler(myDbHandler);

        this.mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        this.mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        ((TextView) findViewById(R.id.deviceAddress)).setText(this.mDeviceAddress);
        mConnectionState = (TextView) findViewById(R.id.connectionState);
        mIncomingText = (TextView) findViewById(R.id.incomingText);
        mIncomingText.setMovementMethod(new ScrollingMovementMethod());
        mOutgoingText = (EditText) findViewById(R.id.outgoingText);
        mOutgoingText.setMovementMethod(new ScrollingMovementMethod());
        mOutgoingText.addTextChangedListener(this.mOutgoingTextWatcher);
//        mOutgoingText.setText("AT");
        mClearOutgoingButton = (Button) findViewById(R.id.clearOutgoingButton);
        mClearOutgoingButton.setOnClickListener(this.mClearOutgoingButtonListener);
        mClearIncomingButton = (Button) findViewById(R.id.clearIncomingButton);
        mClearIncomingButton.setOnClickListener(this.mClearIncomingButtonListener);
//        getActionBar().setTitle(this.mDeviceName);
//        getActionBar().setDisplayHomeAsUpEnabled(true);


        findViewById(R.id.atbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ((EditText) findViewById(R.id.outgoingText)).setText("ATD\n");
                    }
                }, 2000);
            }
        });
        findViewById(R.id.atdn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ((EditText) findViewById(R.id.outgoingText)).setText("AT\n");
                    }
                }, 2000);
            }
        });
        sharedPreferenceslucky = getSharedPreferences("datanewlucky",MODE_PRIVATE);
        editnew = sharedPreferenceslucky.edit();
        findViewById(R.id.atokdn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        ((EditText) findViewById(R.id.outgoingText)).setText("ATDOK\r\n");
                    }
                }, 2000);
            }
        });
        bindService(new Intent(this, BluetoothService.class), this.mServiceConnection, newIntent);
        findViewById(R.id.datanew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WalletLuckyYUY","between ="+String.valueOf(senddata));
                if(senddata.length() !=0){

                    Log.d("WalletLuckyYUY","between ="+String.valueOf(senddata));
                    String[] text_data = senddata.split("\n");
                    for(int i=0;i<text_data.length;i++){
                    Log.d("WalletLuckyYUY"," LUcky ="+text_data[i].toString());


                        text_id  =text_data[i].split(",");

                        Log.d("WalletsenddataLUcky","between new id new 78 = =  "+itemsid.toString());
                        for(int j=0;j<text_id.length;j=j+10){

                            if(text_id[j].length() !=0){
                                String datanewdata = text_id[j];

                                if(datanewdata.length() >=3){
                                    itemsid.add(text_id[j]);
//                                    user.setPasentid(text_id[j]);

                                    if(text_id.length >=j+2){
                                        items_num.add(text_id[j+2]);
//                                        user.setPasentnum(text_id[j+2]);
                                    }
                                    if(text_id.length >=j+3){
                                        items_date.add(text_id[j+1]);
//                                        user.setPasentdate(text_id[j+1]);
//                                        user.setAlldata("");
                                    }


//                                    int h = myDbHandler.insertUser(user);
//                                    Log.d("WalletLuckyYUYnew", "inseart check = " + h);
                                    Log.d("WalletsenddataLUcky","between new id new 786 = =  "+itemsid.toString());
                                }



                            }
//                            items_num.add(text_id[j+2]);
//
//                            items_date.add(text_id[j+1]);





                        }
//                        for(int j=2;j<text_id.length;j=j+9){
//                            items_num.add(text_id[j]);
////                        for(int a=0;a<text_id.length;a=a+10){
////
//                            Log.d("WalletLuckyYUY","between new num new 786 = =  "+items_num.toString());
////                        }
//
//
//                        }
//                        for(int j=1;j<text_id.length;j=j+9){
//
//                            items_date.add(text_id[j]);
////                        for(int a=0;a<text_id.length;a=a+10){
////
//                            Log.d("WalletLuckyYUY","between new data new 786 = =  "+items_date.toString());
////                        }
//
//
//                        }



                        if(i == text_data.length -1){
                            for(int m=0;m<3;m++){
//                                FirstTableData user = new FirstTableData();
//                                user.setPasentid(itemsid.get(i));
//                                user.setPasentnum(items_num.get(i));
//                                user.setPasentdate(items_date.get(i));
//                                user.setAlldata("");
//                                int h = myDbHandler.insertUser(user);
//                                Log.d("WalletLuckyYUYnew", "inseart check = " + h);
                            }
                            Log.d("WalletsenddataLUcky","between new new new 786 size = =  "+itemsid.toString());
                            Log.d("WalletsenddataLUcky","between new new new 786 num = =  "+items_num.toString());
                            Log.d("WalletsenddataLUcky","between new new new 786 date = =  "+items_date.toString());

                           Intent intent = new Intent(SendActivity.this, IDActivity.class);
                           intent.putStringArrayListExtra("id",itemsid);
                            intent.putStringArrayListExtra("num",items_num);
                            intent.putStringArrayListExtra("date",items_date);

                           startActivity(intent);
                        }
                    }
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ((EditText) findViewById(R.id.outgoingText)).setText("ATD\n");

            }
        }, 2000);



    }

    public int newIntent = 1;

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        registerReceiver(this.mGattUpdateReceiver, makeGattUpdateIntentFilter());

        if (this.mBluetoothLeService != null) {
            Log.d(TAG, "Connect request result = " + this.mBluetoothLeService.connect(this.mDeviceAddress));
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        editnew.putString("data",mIncomingText.getText().toString());
        editnew.apply();
        unregisterReceiver(this.mGattUpdateReceiver);

//        SharedPreferences sharedPreferences = getSharedPreferences("datanew",MODE_PRIVATE);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.putString("data",NewData);
//        edit.apply();
    }




    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        editnew.putString("data",mIncomingText.getText().toString());
        editnew.apply();
        unbindService(this.mServiceConnection);
        this.mBluetoothLeService = null;
//
//        SharedPreferences sharedPreferences = getSharedPreferences("datanew",MODE_PRIVATE);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.putString("data",NewData);
//        edit.apply();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (this.mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case R.id.menu_connect /*2131230730*/:
                this.mBluetoothLeService.connect(this.mDeviceAddress);
                return true;
            case R.id.menu_disconnect /*2131230731*/:
                this.mBluetoothLeService.disconnect();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothService.ACTION_DATA_WRITTEN);
        return intentFilter;
    }

    /* access modifiers changed from: private */
    public void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            public void run() {
                SendActivity.this.mConnectionState.setText(resourceId);
            }
        });
    }

    /* access modifiers changed from: private */
    public void clearUI() {
        this.mIncomingText.setText((CharSequence) null);
        this.mOutgoingText.setText((CharSequence) null);
    }

    public String NewData;
    String senddata="";
    boolean databoolnew = true;
    /* access modifiers changed from: private */
    public ArrayList<String> itemsid= new ArrayList<String>();
    public ArrayList<String> items_num= new ArrayList<String>();
    public ArrayList<String> items_date= new ArrayList<String>();
    public ArrayList<String> row_date= new ArrayList<String>();
    public String[] text_id;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor edit;
    public Set<String> set = new HashSet<String>();;
    public void appendData(String data) {

        sharedPreferences =getSharedPreferences("lucky",MODE_PRIVATE);




        if (data != null) {
            mIncomingText.append(data);
            NewData = data;

            row_date.add(mIncomingText.getText().toString().trim());
//            Gson gson = new Gson();
//            String json = gson.toJson(row_date);
//            editnew.putString("data",mIncomingText.getText().toString());
            editnew.putString("datakey", mIncomingText.getText().toString());
            editnew.apply();
            String newnewdata = mIncomingText.getText().toString().trim();
            if(newnewdata.length()>8){


                int n = newnewdata.length();
                Log.d("WalletLUckysss","SJAPANData == "+newnewdata.substring(n - 8));
                String newdata = mIncomingText.getText().toString().substring(n - 8);
                Log.d("WalletLUckysss","SJAPANJAPANData == "+newdata);
                Log.d("WalletLUckysssDATA_END","SJAPANJAPANData 2 == "+newdata);
                Log.d("WalletLUckysssDATA_END","SJAPANJAPANData 231 == "+newdata.indexOf("DATA_END"));

                if(newdata.indexOf("DATA_END") > -1){
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDataMDLP.setValue("ATDOK\r\n");
                            mBluetoothLeService.writeCharacteristic(mDataMDLP);
//                        ((EditText) findViewById(R.id.outgoingText)).setText("ATDOK\n");
                        }
                    }, 2000);
                }
                datanewdata(newdata);


            }

            if(sharedPreferences.getBoolean("check",false)){
                senddata = senddata + data;


//                if(senddata.startsWith("ATOKRES_STR")){
                    Log.d("WalletsenddataLUcky","appendData 1 == "+senddata);
                    Log.d("WalletsenddataLUcky","appendData 1 == "+senddata);

                    if(senddata.indexOf("RES_END") > -1){
                       edit =sharedPreferences.edit();
                        edit.putBoolean("check",false);
                        edit.apply();
                        Log.d("WalletsenddataLUcky","appendData 2 == "+senddata);
                        senddata = senddata.substring(senddata.indexOf("RES_STR") + 9);

                        senddata = senddata.substring(0, senddata.indexOf("RES_END"));
                        Log.d("sjdfljdslfj","appendData 3 == "+senddata);
                        Log.d("WalletsendLUckynew","appendData == "+senddata);

                        Log.d("WalletsenddataLUcky","appendData == "+senddata);
                    }
//                }
            }else{}


        }
    }

    private void datanewdata(String newdata) {
       if(newdata.equals("DATA_END")){
        Log.d("WalletLUckysssDATA_END","SJAPANJAPANData 2 == "+newdata);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataMDLP.setValue("ATDOK\r\n");
                mBluetoothLeService.writeCharacteristic(mDataMDLP);
//                        ((EditText) findViewById(R.id.outgoingText)).setText("ATDOK\n");
            }
        }, 2000);

                }
    }

    public String data = "ATOKRES_START\n" +
            "101,21-NOV-2020,12344321,M1,M2,M3,M4,M6,M7,M8\n" +
            "102,21-NOV-2020,12344321,M1,M2,M3,M4,M6,M7,M8\n" +
            "103,21-NOV-2020,12344321,M1,M2,M3,M4,M6,M7,M8\n" +
            "104,21-NOV-2020,12344321,M1,M2,M3,M4,M6,M7,M8\n" +
            "RES_END";
    /* access modifiers changed from: private */
    public void findMldpGattService(List<BluetoothGattService> gattServices) {
        if (gattServices == null) {
            Log.d(TAG, "findMldpGattService found no Services");
            return;
        }
        this.mDataMDLP = null;
        Iterator intss = gattServices.iterator();
        while (true) {
            if (!intss.hasNext()) {
                break;
            }
            BluetoothGattService gattService = (BluetoothGattService) intss.next();
            if (gattService.getUuid().toString().equals(MLDP_PRIVATE_SERVICE)) {
                for (BluetoothGattCharacteristic gattCharacteristic : gattService.getCharacteristics()) {
                    String uuid = gattCharacteristic.getUuid().toString();
                    if (uuid.equals(MLDP_DATA_PRIVATE_CHAR)) {
                        this.mDataMDLP = gattCharacteristic;
                        int characteristicProperties = gattCharacteristic.getProperties();



                        if ((characteristicProperties & 48) > 0) {
                            Log.d(TAG, "findMldpGattService PROPERTY_NOTIFY | PROPERTY_INDICATE");
                            this.mBluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
                        }
                        if ((characteristicProperties & 12) > 0) {
                            Log.d(TAG, "findMldpGattService PROPERTY_WRITE | PROPERTY_WRITE_NO_RESPONSE");
                        }
                        Log.d(TAG, "findMldpGattService found MLDP service and characteristics");
                    } else if (uuid.equals(MLDP_CONTROL_PRIVATE_CHAR)) {
                    }
                }
            }
        }
        if (this.mDataMDLP == null) {
            Toast.makeText(this, R.string.mldp_not_supported, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "findMldpGattService found no MLDP service");
            finish();
        }
    }
}