package com.example.aidlservice_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 潘硕 on 2017/11/21.
 */

public class ReataurantService extends Service {
    private List<CustomerClient> mClientsList = new ArrayList<>();

    private RemoteCallbackList<NotifyCallBack> mCallBacks = new RemoteCallbackList<>();
    private final RestaurantAidlInterface.Stub mBinder = new RestaurantAidlInterface.Stub(){
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        public void join(IBinder token, String name) throws RemoteException{
            CustomerClient cl = new CustomerClient(token,name);
            mClientsList.add(cl);
            notifyCallBack(name,true);
        }
        public void leave() throws RemoteException{
            int length = mClientsList.size();
            int randomIndex = new Random().nextInt(length-1);
            mClientsList.remove(randomIndex);
            notifyCallBack(mClientsList.get(randomIndex).mCustomerName,false);
        }
        public void registerCallBack(NotifyCallBack cb) throws RemoteException{
            mCallBacks.register(cb);
        }
        public void unregisterCallBack(NotifyCallBack cb) throws RemoteException{
            mCallBacks.unregister(cb);
        }
    };

    private void notifyCallBack(String customerName, boolean joinOrLeave) {
        final int len = mCallBacks.beginBroadcast();
        for (int i=0;i<len;i++){
            mCallBacks.getBroadcastItem(i).notifyMianUiThread(customerName,joinOrLeave);
        }
        mCallBacks.finishBroadcast();
    }
    public void onDestroy(){
        mCallBacks.kill();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private class CustomerClient {
        private final IBinder mToken;
        public String mCustomerName;

        public CustomerClient(IBinder mToken, String mCustomerName) {
            this.mCustomerName = mCustomerName;
            this.mToken = mToken;
        }
        public void binderDied(){
            if (mClientsList.indexOf(this)>=0){
                mClientsList.remove(this);
            }
        }
    }
}
