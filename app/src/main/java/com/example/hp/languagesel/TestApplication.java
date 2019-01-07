package com.example.hp.languagesel;

import android.app.Application;

public class TestApplication extends Application {
    private static TestApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized TestApplication getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReciever.ConnectionRecieverListener listener) {
        ConnectionReciever.connectionRecieverListener = listener;
    }
}
