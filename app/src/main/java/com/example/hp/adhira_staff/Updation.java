package com.example.hp.adhira_staff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hp.adhira_staff.Camera.ScannerQr;

/**
 * Created by HP on 2/19/2018.
 */

public class Updation extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Updation.this, ScannerQr.class));
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomsheet);
        TextView material, color;
       String s[]=getIntent().getStringArrayExtra("code");
       material=(TextView)findViewById(R.id.material);
       color=(TextView)findViewById(R.id.color);
        if(s[1].equals("S"))
        {
            material.setText("Silk");
        }
        if(s[2].equals("R"))
        {
            color.setText("Red/"+s[3]);
        }
    }
}
