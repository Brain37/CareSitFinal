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

public class UserProfileEdit extends AppCompatActivity
{
    EditText fnameET;
    EditText lnameET;
    EditText cityET;
    EditText stateET;
    EditText zipET;
    EditText phoneET;
    EditText addressET;
    Button saveButton;
    Firebase profileRef;
    Iterator<DataSnapshot> newSnapshot;
    Iterator<DataSnapshot> theNewSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);
        fnameET = (EditText)this.findViewById(R.id.fnameET);
        lnameET = (EditText)this.findViewById(R.id.lnameET);
        cityET = (EditText)this.findViewById(R.id.cityET);
        stateET = (EditText)this.findViewById(R.id.stateET);
        zipET = (EditText)this.findViewById(R.id.zipET);
        phoneET = (EditText)this.findViewById(R.id.phoneET);
        addressET = (EditText)this.findViewById(R.id.addressET);
        saveButton = (Button)this.findViewById(R.id.saveButton);
        profileRef = Core.myFirebaseRef.child("user_profile");



        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserProfile profile = new UserProfile(fnameET.getText().toString(),lnameET.getText().toString(),cityET.getText().toString(),zipET.getText().toString(), stateET.getText().toString(), phoneET.getText().toString(), addressET.getText().toString(),Core.myFirebaseRef.getAuth().getUid().toString());
                while(theNewSnapshot.hasNext())
                {
                    DataSnapshot temp = theNewSnapshot.next();
                    System.out.println(temp.child("uid").getValue().toString());
                    System.out.println(Core.myFirebaseRef.getAuth().getUid());
                    if(temp.child("uid").getValue().toString().equals(Core.myFirebaseRef.getAuth().getUid()))
                    {
                        System.out.println("got here");
                        profileRef.child(temp.getKey()).setValue(profile);
                        return;
                    }
                }
                profileRef.push().setValue(profile);
            }
        });

        profileRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                newSnapshot = snapshot.getChildren().iterator();
                theNewSnapshot = snapshot.getChildren().iterator();
                while(newSnapshot.hasNext())
                {
                    DataSnapshot temp = newSnapshot.next();
                    if(temp.child("uid").getValue().toString().equals(Core.myFirebaseRef.getAuth().getUid()))
                    {
                        fnameET.setText(temp.child("fname").getValue().toString());
                        lnameET.setText(temp.child("lname").getValue().toString());
                        cityET.setText(temp.child("city").getValue().toString());
                        stateET.setText(temp.child("state").getValue().toString());
                        zipET.setText(temp.child("zip").getValue().toString());
                        phoneET.setText(temp.child("phone").getValue().toString());
                        addressET.setText(temp.child("address").getValue().toString());
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
