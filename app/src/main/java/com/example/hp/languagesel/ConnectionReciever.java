package com.example.hp.languagesel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionReciever extends BroadcastReceiver {
    public static ConnectionRecieverListener connectionRecieverListener;
    public ConnectionReciever(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
        boolean isConnected=activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        if (connectionRecieverListener!=null){
            connectionRecieverListener.onNetworkConnectionChanged(isConnected);
        }
    }
    public static boolean isConnected(){
        ConnectivityManager cm=(ConnectivityManager)TestApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }
    public interface ConnectionRecieverListener{
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
