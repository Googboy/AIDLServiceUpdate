package com.example.aidl_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.aidl.IPerson;

/**
 * Created by 潘硕 on 2017/11/20.
 */

public class AIDLService extends Service {
    private static final String TAG = "AIDLService";
    IPerson.Stub stub = new IPerson.Stub(){

        @Override
        public String greet(String someone) throws RemoteException {
            return "Hello,"+someone;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
