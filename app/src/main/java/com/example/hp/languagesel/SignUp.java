package com.example.hp.languagesel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar progressbar;
    private EditText editTextEmail,editTextPass;
    Button SignUp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail=(EditText)findViewById(R.id.SignUpEmail);
        editTextPass=(EditText)findViewById(R.id.SignUpPassword);
        SignUp=(Button)findViewById(R.id.buttonSignUp);
        progressbar=(ProgressBar)findViewById(R.id.SignUpProgressBar);
        SignUp.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.SignIn_text).setOnClickListener(this);
    }

    private void registerUser(){
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
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    FirebaseUser mUser = mAuth.getCurrentUser();
                    mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Verification Email Sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (mUser.isEmailVerified()) {
                        finish();
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"Email already registered",Toast.LENGTH_SHORT).show();
                    }
                    else{
                    Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                }
            }}}
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignUp:
                registerUser();
                break;
            case R.id.SignIn_text:
                finish();
                startActivity(new Intent(this,SignIn.class));
                break;

        }
    }
}
