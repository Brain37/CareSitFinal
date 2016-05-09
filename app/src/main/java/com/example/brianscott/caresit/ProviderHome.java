package com.example.brianscott.caresit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Context;

import java.util.ArrayList;
import java.util.Iterator;

public class ProviderHome extends AppCompatActivity
{
    ListView notAccepted;
    ListView notCompleted;
    ListView completed;
    ArrayList<String> stringRequestsNotAccepted;
    ArrayList<String> stringRequestsNotCompleted;
    ArrayList<String> stringRequestsCompleted;
    ArrayList<Request> requestsNotAccepted;
    ArrayList<Request> requestsNotCompleted;
    ArrayList<Request> requestsCompleted;
    ArrayAdapter<String> notAcceptedAdapter;
    ArrayAdapter<String> notCompletedAdapter;
    ArrayAdapter<String> completedAdapter;
    DataSnapshot serviceRequestSnapshot;
    Iterator<DataSnapshot> serviceRequests;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);
        Firebase.setAndroidContext(this);
        final ProviderHome the = this;
        stringRequestsNotAccepted = new ArrayList<String>();
        stringRequestsNotCompleted = new ArrayList<String>();
        stringRequestsCompleted = new ArrayList<String>();

        notAccepted = (ListView) this.findViewById(R.id.notAcceptedLV);
        notCompleted = (ListView)this.findViewById(R.id.notCompletedLV);
        completed = (ListView)this.findViewById(R.id.completedLV);

        notAcceptedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringRequestsNotAccepted);
        notAccepted.setAdapter(notAcceptedAdapter);
        notAccepted.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(the);
                builder1.setMessage(String.valueOf(parent.getItemAtPosition(position)) + "\n" + "\n" + "Do you want to accept this request?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                for(Request r: requestsNotAccepted)
                                {
                                    String stringRequest = "Start: " + r.getStartTimeAndDate() + " for " + r.getLength() + " hours with: " + r.getNumKids() + " kids";
                                    String toCompare = r.getStartTimeAndDate();
                                    if(stringRequest.equalsIgnoreCase(String.valueOf(parent.getItemAtPosition(position))))
                                    {
                                        r.setProvider(Core.myFirebaseRef.getAuth().getUid());
                                        while(serviceRequests.hasNext())
                                        {
                                            DataSnapshot temp = serviceRequests.next();
                                            if(temp.child("startTimeAndDate").getValue().toString().equalsIgnoreCase(toCompare))
                                            {
                                                Core.myFirebaseRef.child("service_requests").child(temp.getKey()).setValue(r);
                                            }
                                        }
                                    }
                                }
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        notCompletedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringRequestsNotCompleted);
        notCompleted.setAdapter(notCompletedAdapter);
        notCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(the);
                builder1.setMessage("Do you want to complete this request?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                for(Request r: requestsNotCompleted)
                                {
                                    String stringRequest = "Start: " + r.getStartTimeAndDate() + " for " + r.getLength() + " hours with: " + r.getNumKids() + " kids";
                                    String toCompare = r.getStartTimeAndDate();
                                    if(stringRequest.equals(String.valueOf(parent.getItemAtPosition(position))))
                                    {
                                        r.setCompleted("true");
                                        while(serviceRequests.hasNext())
                                        {
                                            DataSnapshot temp = serviceRequests.next();
                                            if(temp.child("startTimeAndDate").getValue().toString().equalsIgnoreCase(toCompare))
                                            {
                                                System.out.println(temp.getKey());
                                                Core.myFirebaseRef.child("service_requests").child(temp.getKey()).setValue(r);
                                            }
                                        }
                                    }
                                }
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

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
                serviceRequestSnapshot = snapshot;
                serviceRequests = serviceRequestSnapshot.getChildren().iterator();
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
                    if(newRequest.getProvider().equals(Core.myFirebaseRef.getAuth().getUid()) && newRequest.getCompleted().equals("true"))
                    {
                        requestsCompleted.add(newRequest);
                    }
                    else if(newRequest.getProvider().equals(Core.myFirebaseRef.getAuth().getUid()) && newRequest.getCompleted().equals("false"))
                    {
                        requestsNotCompleted.add(newRequest);
                    }
                    else if(newRequest.getProvider().equals("n/a"))
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

    }

    public void logoutButtonPressed(View v)
    {
        Core.myFirebaseRef.unauth();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void editProfileButtonPressed(View v)
    {
        startActivity(new Intent(getApplicationContext(), ProviderProfileEdit.class));
    }

}
