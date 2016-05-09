package com.example.brianscott.caresit;

/**
 * Created by f00310341 on 5/8/2016.
 */
public class UserProfile
{
    private String fname;
    private String lname;
    private String city;
    private String zip;
    private String state;
    private String phone;
    private String address;
    private String uid;

    public UserProfile(String fname, String lname, String city, String zip, String state, String phone, String address, String uid)
    {
        this.fname = fname;
        this.lname = lname;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.phone = phone;
        this.address = address;
        this.uid = uid;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getUid() {
        return uid;
    }
}
