package com.example.hp.languagesel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EnglishActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth mAuth;
    ImageButton btnFood;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mt;
    private TextView UserNameNavDrawer;
    public static final String mypreference = "MY_PREFS_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);
        mAuth=FirebaseAuth.getInstance();
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        mt=(Toolbar)findViewById(R.id.tool_header);
        setSupportActionBar(mt);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        UserNameNavDrawer=(TextView)navigationView.getHeaderView(0).findViewById(R.id.username_nav_drawer);
        btnFood=(ImageButton) findViewById(R.id.btn_food_english);
            loadUserInformation();
            btnFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(EnglishActivity.this, "this is food button", Toast.LENGTH_SHORT).show();
                }
            });
        }


    @Override
    protected void onStart() {
        super.onStart();
            if (mAuth.getCurrentUser() == null) {
                finish();
                startActivity(new Intent(this, SignIn.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void loadUserInformation(){
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null) {
            String F_email = user.getEmail();
            UserNameNavDrawer.setText(F_email);
        }

    }

    boolean twice=false;
    @Override
    public void onBackPressed() {

        if (twice==true){
            SharedPreferences prefs=getSharedPreferences("mypreference",MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putString("lastOpenedActivity",getClass().getName());
            editor.apply();
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        Toast.makeText(this,"Please press BACK again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
            }
        },3000);
        twice=true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.profile)
        {
            Toast.makeText(EnglishActivity.this,"This is profile",Toast.LENGTH_SHORT).show();
            mDrawerLayout.closeDrawer(GravityCompat.START,false);
        }
        if(id==R.id.all_courses)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START,false);
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }
        if(id==R.id.chat)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START,false);
            String[] emails = new String[]{
                    "collegeProjectAKM@gmail.com"};
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.putExtra(Intent.EXTRA_EMAIL,emails);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Issues");
            intent.setData(Uri.parse("mailto:"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }else{ Toast.makeText(this,"No email client installed in this device.",Toast.LENGTH_SHORT).show();
            }
        }
        if(id==R.id.settings)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START,false);
            Toast.makeText(this,"This is settings",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.Logout)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START,false);
            final AlertDialog.Builder builder=new AlertDialog.Builder(EnglishActivity.this);
            builder.setCancelable(false);
            builder.setMessage("Are you sure you want to log out?");


            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(EnglishActivity.this,SignIn.class));
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();


        }
        if(id==R.id.share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plane");
            i.putExtra(Intent.EXTRA_TEXT,"My new app at");
            startActivity(Intent.createChooser(i,"Share Via"));
        }
        return false;
    }
    }

