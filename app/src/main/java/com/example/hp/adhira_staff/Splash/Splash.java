package com.example.hp.adhira_staff.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.adhira_staff.Credentials.Login;
import com.example.hp.adhira_staff.R;

/**
 * Created by HP on 2/18/2018.
 */

public class Splash extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this,Login.class));
                finish();
            }
        },2000);
    }
}
