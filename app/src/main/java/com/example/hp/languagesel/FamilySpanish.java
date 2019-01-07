package com.example.hp.languagesel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FamilySpanish extends AppCompatActivity{
    private TextView SpanishStat;
    private TextView ConvStat;
    private Button mNext;
    private FirebaseDatabase mConvStatementsRef=FirebaseDatabase.getInstance();;
    private FirebaseDatabase mStatementRef=FirebaseDatabase.getInstance();
    int SetNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_spanish);

        SpanishStat=(TextView)findViewById(R.id.text_family_spanish);
        ConvStat=(TextView)findViewById(R.id.convText_family_spanish);
        mNext=(Button)findViewById(R.id.Nextbtn_family_spanish);
        mNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatements();
            }
        });

    }
    public void updateStatements(){

        if(SetNum<=10) {
            DatabaseReference mySRef = mStatementRef.getReferenceFromUrl("https://languagesel.firebaseio.com/" + SetNum + "/statements");
            mySRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String statements = dataSnapshot.getValue(String.class);
                    SpanishStat.setText(statements);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            DatabaseReference myCSRef = mConvStatementsRef.getReferenceFromUrl("https://languagesel.firebaseio.com/" + SetNum + "/english");
            myCSRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String English = dataSnapshot.getValue(String.class);
                    ConvStat.setText(English);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        SetNum++;
        if (SetNum==11){
            Toast.makeText(FamilySpanish.this,"You Have come to end.Kindly Choose some other course",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(FamilySpanish.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }
}
