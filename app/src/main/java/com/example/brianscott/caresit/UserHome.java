package com.example.brianscott.caresit;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;

public class UserHome extends AppCompatActivity
{
    EditText startTimeAndDate;
    EditText length;
    EditText numKids;
    EditText description;
    ListView notAccepted;
    ListView notCompleted;
    ListView completed;
    TextWatcher theWatcher;
    Button purchaseButton;
    Firebase serviceRequestsRef;
    ArrayList<String> stringRequestsNotAccepted;
    ArrayList<String> stringRequestsNotCompleted;
    ArrayList<String> stringRequestsCompleted;
    ArrayList<Request> requestsNotAccepted;
    ArrayList<Request> requestsNotCompleted;
    ArrayList<Request> requestsCompleted;
    ArrayAdapter<String> notAcceptedAdapter;
    ArrayAdapter<String> notCompletedAdapter;
    ArrayAdapter<String> completedAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Firebase.setAndroidContext(this);

        stringRequestsNotAccepted = new ArrayList<String>();
        stringRequestsNotCompleted = new ArrayList<String>();
        stringRequestsCompleted = new ArrayList<String>();

        notAccepted = (ListView) this.findViewById(R.id.notAcceptedLV);
        notCompleted = (ListView)this.findViewById(R.id.notCompletedLV);
        completed = (ListView)this.findViewById(R.id.completedLV);

        notAcceptedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringRequestsNotAccepted);
        notAccepted.setAdapter(notAcceptedAdapter);
        notCompletedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringRequestsNotCompleted);
        notCompleted.setAdapter(notCompletedAdapter);
        completedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringRequestsCompleted);
        completed.setAdapter(completedAdapter);

        requestsNotAccepted = new ArrayList<Request>();
        requestsNotCompleted = new ArrayList<Request>();
        requestsCompleted = new ArrayList<Request>();

        Core.myFirebaseRef.child("service_requests").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                Iterator<DataSnapshot> newSnapshot = snapshot.getChildren().iterator();
                if(!stringRequestsNotAccepted.isEmpty())
                {
                    requestsNotAccepted.clear();
                    stringRequestsNotAccepted.clear();
                }
                if(!stringRequestsNotCompleted.isEmpty())
                {
                    requestsNotCompleted.clear();
                    stringRequestsNotCompleted.clear();
                }
                if(!stringRequestsCompleted.isEmpty())
                {
                    stringRequestsCompleted.clear();
                    requestsCompleted.clear();
                }

                while(newSnapshot.hasNext())
                {
                    Request newRequest = new Request();
                    Iterator<DataSnapshot> childSnapshot = newSnapshot.next().getChildren().iterator();
                    while (childSnapshot.hasNext())
                    {
                        DataSnapshot temp = childSnapshot.next();
                        if (temp.getKey().equals("startTimeAndDate"))
                        {
                            newRequest.setStartTimeAndDate(temp.getValue().toString());
                        }
                        if (temp.getKey().equals("numKids"))
                        {
                            newRequest.setNumKids(temp.getValue().toString());
                        }
                        if (temp.getKey().equals("length"))
                        {
                            newRequest.setLength(temp.getValue().toString());
                        }
                        if (temp.getKey().equals("description"))
                        {
                            newRequest.setDescription(temp.getValue().toString());
                        }
                        if (temp.getKey().equals("provider"))
                        {
                            newRequest.setProvider(temp.getValue().toString());
                        }
                        if (temp.getKey().equals("user"))
                        {
                            newRequest.setUser(temp.getValue().toString());
                        }
                        if (temp.getKey().equals("completed"))
                        {
                            newRequest.setCompleted(temp.getValue().toString());
                        }
                    }
                    if(newRequest.getUser().equals(Core.myFirebaseRef.getAuth().getUid()) && !newRequest.getProvider().equals("n/a") && newRequest.getCompleted().equals("true"))
                    {
                        requestsCompleted.add(newRequest);
                    }
                    else if(newRequest.getUser().equals(Core.myFirebaseRef.getAuth().getUid()) && !newRequest.getProvider().equals("n/a") && newRequest.getCompleted().equals("false"))
                    {
                        requestsNotCompleted.add(newRequest);
                    }
                    else if(newRequest.getUser().equals(Core.myFirebaseRef.getAuth().getUid()) && newRequest.getProvider().equals("n/a"))
                    {
                        requestsNotAccepted.add(newRequest);
                    }
                }

                for(Request r : requestsCompleted)
                {
                    String stringRequest = "Start: " + r.getStartTimeAndDate() + " for " + r.getLength() + " hours with: " + r.getNumKids() + " kids";
                    stringRequestsCompleted.add(stringRequest);
                }
                for(Request r : requestsNotCompleted)
                {
                    String stringRequest = "Start: " + r.getStartTimeAndDate() + " for " + r.getLength() + " hours with: " + r.getNumKids() + " kids";
                    stringRequestsNotCompleted.add(stringRequest);
                }
                for(Request r : requestsNotAccepted)
                {
                    String stringRequest = "Start: " + r.getStartTimeAndDate() + " for " + r.getLength() + " hours with: " + r.getNumKids() + " kids";
                    stringRequestsNotAccepted.add(stringRequest);
                }
                notAcceptedAdapter.notifyDataSetChanged();
                notCompletedAdapter.notifyDataSetChanged();
                completedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError error)
            {

            }

        });


        startTimeAndDate = (EditText) this.findViewById(R.id.startTimeEditText);
        length = (EditText) this.findViewById(R.id.lengthET);
        numKids = (EditText) this.findViewById(R.id.numKidsET);
        description = (EditText) this.findViewById(R.id.descriptionEditText);
        final TextView costTV = (TextView)this.findViewById(R.id.costTV);
        theWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(!numKids.getText().toString().equals("") && !length.getText().toString().equals("")) {
                    long intNumKids = Long.parseLong(numKids.getText().toString());
                    long intLength = Long.parseLong(length.getText().toString());
                    costTV.setText("Cost: " + Core.computeCost(intNumKids, intLength));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        numKids.addTextChangedListener(theWatcher);

        purchaseButton = (Button)this.findViewById(R.id.purchaseButton);

        serviceRequestsRef = Core.myFirebaseRef.child("service_requests");

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request newRequest = new Request();
                newRequest.setLength(length.getText().toString());
                newRequest.setDescription(description.getText().toString());
                newRequest.setNumKids(numKids.getText().toString());
                newRequest.setStartTimeAndDate(startTimeAndDate.getText().toString());
                newRequest.setCompleted("false");
                newRequest.setUser(Core.myFirebaseRef.getAuth().getUid());
                newRequest.setProvider("n/a");

                serviceRequestsRef.push().setValue(newRequest);

                startTimeAndDate.setText("");
                numKids.setText("");
                length.setText("");
                description.setText("");
            }
        });
    }
    public void logoutButtonPressed(View v)
    {
        Core.myFirebaseRef.unauth();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void editProfileButtonPressed(View v)
    {
        startActivity(new Intent(getApplicationContext(), UserProfileEdit.class));
    }
}
