package com.example.hp.adhira_staff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.adhira_staff.Camera.ScannerQr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Updation extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Updation.this, ScannerQr.class));
        finish();
    }
    DatabaseReference dref;
    int i;
    Button b;
    EditText ed;
    String s1,s2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomsheet);
        final TextView material, color;
       String s[]=getIntent().getStringArrayExtra("code");
       dref= FirebaseDatabase.getInstance().getReference();
       material=(TextView)findViewById(R.id.material);
       color=(TextView)findViewById(R.id.color);
       b=(Button)findViewById(R.id.button2);
       ed=(EditText)findViewById(R.id.cut);
       for(i=0;i<s.length;i++)
       {
           dref=dref.child(s[i]);
       }
       System.out.println("boww"+dref.toString());
       dref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               MaterialAdapter map=dataSnapshot.getValue(MaterialAdapter.class);
               material.setText(map.getMet());
               color.setText(map.getMcolor());
               s2=map.getMavy().toString();

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int num1=Integer.parseInt(s2);
               int num2=Integer.parseInt(ed.getText().toString());
               int num3=num1-num2;
               s1=String.valueOf(num3);
               dref.child("mavy").setValue(s1);
               startActivity(new Intent(Updation.this,ScannerQr.class));
           }
       });

    }
}
