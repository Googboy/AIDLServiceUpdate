package com.example.aidl_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aidl.IPerson;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IPerson person;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ServiceConnection","onServiceConnected() called");
            person = IPerson.Stub.asInterface(service);
            String retVal = null;
            try {
                retVal = person.greet("scott");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainActivity.this,retVal,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("connection","Disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mIntent = new Intent();
        mIntent.setAction("android.intent.action.AIDLService");
        Intent eintent = new Intent(getExplicitIntent(this,mIntent));
        bindService(eintent,conn, Context.BIND_AUTO_CREATE);
    }

    private static Intent getExplicitIntent(MainActivity mainActivity, Intent mIntent) {
        PackageManager pm = mainActivity.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(mIntent,0);
        if (resolveInfo == null||resolveInfo.size() !=1){
            return null;
        }
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName,className);
        Intent explicitIntent = new Intent(mIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
}
