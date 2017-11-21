// NotifyCallBack.aidl
package com.example.aidlservice_client;

// Declare any non-default types here with import statements

interface NotifyCallBack {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void notifyMainUiThread(String name,boolean joinOrLeave);
}
