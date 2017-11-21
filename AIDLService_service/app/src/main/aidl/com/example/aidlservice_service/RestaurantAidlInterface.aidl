// RestaurantAidlInterface.aidl
package com.example.aidlservice_service;

// Declare any non-default types here with import statements

interface RestaurantAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    void join(IBinder token,String name);
    void leave();
    void registerCallBack(NotifyCallBack cb);
    void unregisterCallBack(NotifyCallBack cb);
}
