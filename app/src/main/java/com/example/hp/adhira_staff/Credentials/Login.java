package com.example.hp.adhira_staff.Credentials;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.adhira_staff.Camera.ScannerQr;
import com.example.hp.adhira_staff.R;
import com.example.hp.adhira_staff.SignupAdapter;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by HP on 2/18/2018.
 */

public class Login extends AppCompatActivity {
    Button click;
    TextView signup;
    DatabaseReference dref,dref2;
    TextInputEditText e1,e2;
    String user,pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.login);
        click=(Button)findViewById(R.id.click);
        signup=(TextView)findViewById(R.id.signup);
        e1=(TextInputEditText)findViewById(R.id.editText1);
        e2=(TextInputEditText)findViewById(R.id.editText2);
        Firebase.setAndroidContext(this);
        dref= FirebaseDatabase.getInstance().getReference().child("USERS");
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=e1.getText().toString();
                pass=e2.getText().toString();
                if(user.equals("")&&pass.equals(""))
                {
                    Toast.makeText(Login.this, "CREDENTIAL CANT BE EMPTY", Toast.LENGTH_SHORT).show();
                } else if (user.equals("")) {
                    Toast.makeText(Login.this, "USERNAME CANT BE EMPTY", Toast.LENGTH_SHORT).show();
                }else if(pass.equals("")){
                    Toast.makeText(Login.this, "PASSWORD CANT BE EMPTY", Toast.LENGTH_SHORT).show();
                }
                else {
                    dref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(user)) {
                                dref2 = dref.child(user);
                                dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        SignupAdapter adapter = dataSnapshot.getValue(SignupAdapter.class);
                                        if (user.equals(adapter.getUname())) {
                                            if (pass.equals(adapter.getUpass())) {
                                                startActivity(new Intent(Login.this, ScannerQr.class));
                                            } else {
                                                Toast.makeText(Login.this, "INCORRECT PASSWORD", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(Login.this, "INVALID USERNAME", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Signup.class));
            }
        });

    }

}
