package com.example.hp.languagesel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class  SignIn extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    private EditText editTextEmail,editTextPass;
    private ProgressBar progressbar;
    public static final String mypreference = "MY_PREFS_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextEmail=(EditText)findViewById(R.id.SignUpEmail);
        editTextPass=(EditText)findViewById(R.id.SignUpPassword);
        findViewById(R.id.SignUp_text).setOnClickListener(this);
        findViewById(R.id.buttonSignIn).setOnClickListener(this);
        progressbar=(ProgressBar)findViewById(R.id.SignUpProgressBar);
        mAuth=FirebaseAuth.getInstance();
    }

    public void userLogin(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPass.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid Email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPass.setError("Password is required");
            editTextPass.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextPass.setError("Minimum length of password should be six");
            editTextPass.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser mUser =mAuth.getCurrentUser();
                progressbar.setVisibility(View.GONE);
                if (task.isSuccessful() && mUser.isEmailVerified()) {
                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                }
                else if (task.isSuccessful() && !mUser.isEmailVerified()){
                    Toast.makeText(SignIn.this,"Email not Verified",Toast.LENGTH_SHORT).show();
                }
                
                else if(task.getException()!=null) {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*User already logged in is not required to log in again*/

    @Override
    protected void onStart() {
        super.onStart();
        Class lastActivity;
        if (mAuth.getCurrentUser()!=null)
        {
            SharedPreferences prefs = getSharedPreferences("mypreference", MODE_PRIVATE);
            try {
                lastActivity = Class.forName(prefs.getString("lastOpenedActivity","MainActivity"));
                startActivity(new Intent(this, lastActivity));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //startActivity(new Intent(SignIn.this,MainActivity.class));
            this.finish();
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SignUp_text:
                finish();
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.buttonSignIn:
                userLogin();
                break;

        }

    }
}
