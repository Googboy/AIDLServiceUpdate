package com.example.aidlservice_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnBind,btnGo,btnAdd,btnUnBind;
    private TextView tv_show;
    private RestaurantAidlInterface mService;
    private NotifyCallBack mNotifyCallBack = new NotifyCallBack.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
        public void notifyMainUiThread(String name,boolean joinOrLeave) throws RemoteException{
            String toastStr = "";
            if (joinOrLeave){
                toastStr = name+"进入了餐厅";
            }else {
                toastStr = name+"离开了餐厅";
            }
            tv_show.setText(toastStr);
        }
    };
    private ServiceConnection mServiceConnection  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = RestaurantAidlInterface.Stub.asInterface(service);
            mService.registerCallBack(mNotifyCallBack);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService.unregisterCallBack(mNotifyCallBack);
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBind = (Button) findViewById(R.id.btnBind);
        btnUnBind = (Button) findViewById(R.id.btnUnBind);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnBind.setOnClickListener(this);
        btnUnBind.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnGo.setOnClickListener(this);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBind:
                bindService();
                break;
            case R.id.btnUnBind:
                unbindService();
                break;
            case R.id.btnAdd:
                addCustomer();
                break;
            case R.id.btnGo:
                leaveCustomer();
                break;
        }
    }

    
    public boolean bindService() {
        Intent intent = new Intent(RestaurantAidlInterface.class.getName());
        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
    }
}
