// RestaurantAidlInterface.aidl
package com.example.aidlservice_client;

// Declare any non-default types here with import statements

interface RestaurantAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void join(IBinder token,String name);
        void leave();
        void registerCallBack(NotifyCallBack cb);
        void unregisterCallBack(NotifyCallBack cb);
}
