package com.example.brianscott.caresit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }
    public void logoutButtonPressed(View v)
    {
        Core.myFirebaseRef.unauth();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
