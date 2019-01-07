package com.example.hp.languagesel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView French,English,Spanish,Germen;
    private Toolbar mtoolbar;
    public static final String mypreference = "MY_PREFS_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(mtoolbar);
        mtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        French=(CardView)findViewById(R.id.french_id);
        English=(CardView)findViewById(R.id.English_id);
        Spanish=(CardView)findViewById(R.id.spanish_id);
        Germen=(CardView)findViewById(R.id.germen_id);

        French.setOnClickListener(this);
        English.setOnClickListener(this);
        Spanish.setOnClickListener(this);
        Germen.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId())
        {
            case R.id.french_id:
                i=new Intent(this,FrenchActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.English_id:
                i=new Intent(this,EnglishActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.spanish_id:
                i=new Intent(this,SpanishAcitivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.germen_id:
                i=new Intent(this,GermenActivity.class);
                startActivity(i);
                finish();
                break;
            default:break;
        }

    }
    public void onBackPressed() {
            //moveTaskToBack(true);
            SharedPreferences prefs=getSharedPreferences("mypreference",MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putString("lastOpenedActivity",getClass().getName());
            editor.apply();
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //finish();
            System.exit(1);
        }

}
