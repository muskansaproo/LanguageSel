package com.example.hp.languagesel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity implements ConnectionReciever.ConnectionRecieverListener {
    private static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (checkConnection()) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(WelcomeActivity.this, SignIn.class);
                    startActivity(homeIntent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            AlertDialog.Builder builder=new AlertDialog.Builder(WelcomeActivity.this);
            builder.setTitle("No Internet Connection");
            builder.setMessage("You need to have mobile data to access this.Press ok to exit");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(1);
                    finish();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
        else {
                DialogInterface dialogInterface=new DialogInterface() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void dismiss() {

                    }
                };
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        TestApplication.getInstance().setConnectionListener((WelcomeActivity) this);
    }

    private boolean checkConnection(){
        boolean isConnected=ConnectionReciever.isConnected();
        if (!isConnected){
            AlertDialog.Builder builder=new AlertDialog.Builder(WelcomeActivity.this);
            builder.setCancelable(true);
            builder.setTitle("No Internet Connection");
            builder.setMessage("You need to have mobile data to access this.Press ok to exit");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(1);
                    finish();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        return isConnected;
    }
}
