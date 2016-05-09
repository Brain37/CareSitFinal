package com.example.brianscott.caresit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class Register extends AppCompatActivity
{
    EditText username;
    EditText password;
    Button registerButton;
    Account currentUser;
    Firebase myFirebaseRef;
    Intent registerIntent;
    Intent userHomeIntent;
    Intent providerHomeIntent;
    Intent adminHomeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        registerIntent = new Intent(getApplicationContext(), Register.class);
        userHomeIntent = new Intent(getApplicationContext(), UserHome.class);
        providerHomeIntent = new Intent(getApplicationContext(), ProviderHome.class);
        setContentView(R.layout.activity_register);
        username = (EditText)this.findViewById(R.id.usernameEditText);
        password = (EditText)this.findViewById(R.id.passwordEditText);

        registerButton = (Button)this.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentUser = new Account(username.getText().toString(), password.getText().toString());
                Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler()
                {
                    @Override
                    public void onAuthenticated(AuthData authData)
                    {
                        // Authenticated successfully with payload authData
                        Core.myFirebaseRef.child("role").addValueEventListener(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(DataSnapshot snapshot)
                            {
                                if(snapshot.child(Core.myFirebaseRef.getAuth().getUid()).getValue().toString().equals("user"))
                                {
                                    startActivity(userHomeIntent);
                                }
                                else if(snapshot.child(Core.myFirebaseRef.getAuth().getUid()).getValue().toString().equals("provider"))
                                {
                                    startActivity(providerHomeIntent);
                                }
                                else if(snapshot.child(Core.myFirebaseRef.getAuth().getUid()).getValue().toString().equals("admin"))
                                {
                                    startActivity(adminHomeIntent);
                                }
                            }
                            @Override
                            public void onCancelled(FirebaseError firebaseError)
                            {

                            }
                        });
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError)
                    {
                        // Authenticated failed with error firebaseError
                    }
                };
            }
        });
    }


}
