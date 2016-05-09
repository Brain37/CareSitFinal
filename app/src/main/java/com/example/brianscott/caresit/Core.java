package com.example.brianscott.caresit;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Brian Scott on 4/26/2016.
 */
public class Core
{
    static Firebase myFirebaseRef = new Firebase("https://blazing-heat-8324.firebaseio.com/");
    static long computeCost(long numkids, long length)
    {
        return numkids * 3 * length;
    }
}
