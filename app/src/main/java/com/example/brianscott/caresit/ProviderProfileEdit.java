package com.example.brianscott.caresit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Iterator;

public class ProviderProfileEdit extends AppCompatActivity
{
    EditText pfnameET;
    EditText plnameET;
    EditText pcityET;
    EditText pstateET;
    EditText pzipET;
    EditText pphoneET;
    EditText paddressET;
    Button saveButton;
    Firebase providerProfileRef;
    Iterator<DataSnapshot> newSnapshot;
    Iterator<DataSnapshot> thenewSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_profile_edit);
        Firebase.setAndroidContext(this);
        pfnameET = (EditText)this.findViewById(R.id.pfnameET);
        plnameET = (EditText)this.findViewById(R.id.plnameET);
        pcityET = (EditText)this.findViewById(R.id.pcityET);
        pstateET = (EditText)this.findViewById(R.id.pstateET);
        pzipET = (EditText)this.findViewById(R.id.pzipET);
        pphoneET = (EditText)this.findViewById(R.id.pphoneET);
        paddressET = (EditText)this.findViewById(R.id.paddressET);
        saveButton = (Button)this.findViewById(R.id.saveButton);
        providerProfileRef = Core.myFirebaseRef.child("provider_profile");

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ProviderProfile profile = new ProviderProfile(pfnameET.getText().toString(),plnameET.getText().toString(),pcityET.getText().toString(),pzipET.getText().toString(), pstateET.getText().toString(), pphoneET.getText().toString(), paddressET.getText().toString(),Core.myFirebaseRef.getAuth().getUid().toString());
                while(thenewSnapshot.hasNext())
                {
                    DataSnapshot temp = thenewSnapshot.next();
                    System.out.println(temp.child("puid").getValue().toString());
                    System.out.println(Core.myFirebaseRef.getAuth().getUid());
                    if (temp.child("puid").getValue().toString().equals(Core.myFirebaseRef.getAuth().getUid()))
                    {
                        System.out.println("got here");
                        providerProfileRef.child(temp.getKey()).setValue(profile);
                        return;
                    }
                }
                providerProfileRef.push().setValue(profile);
            }
        });

        providerProfileRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                newSnapshot = snapshot.getChildren().iterator();
                thenewSnapshot = snapshot.getChildren().iterator();

                while(newSnapshot.hasNext())
                {
                    DataSnapshot temp = newSnapshot.next();
                    if(temp.child("puid").getValue().toString().equals(Core.myFirebaseRef.getAuth().getUid()))
                    {
                        pfnameET.setText(temp.child("pfname").getValue().toString());
                        plnameET.setText(temp.child("plname").getValue().toString());
                        pcityET.setText(temp.child("pcity").getValue().toString());
                        pstateET.setText(temp.child("pstate").getValue().toString());
                        pzipET.setText(temp.child("pzip").getValue().toString());
                        pphoneET.setText(temp.child("pphone").getValue().toString());
                        paddressET.setText(temp.child("paddress").getValue().toString());
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });


    }
}
