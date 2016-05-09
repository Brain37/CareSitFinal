package com.example.brianscott.caresit;

/**
 * Created by f00310341 on 5/9/2016.
 */
public class ProviderProfile
{
    private String pfname;
    private String plname;
    private String pcity;
    private String pzip;
    private String pstate;
    private String pphone;
    private String paddress;
    private String puid;

    public ProviderProfile(String fname, String lname, String city, String zip, String state, String phone, String address, String uid)
    {
        this.pfname = fname;
        this.plname = lname;
        this.pcity = city;
        this.pzip = zip;
        this.pstate = state;
        this.pphone = phone;
        this.paddress = address;
        this.puid = uid;
    }

    public String getPuid() {
        return puid;
    }

    public String getPfname() {
        return pfname;
    }

    public String getPlname() {
        return plname;
    }

    public String getPcity() {
        return pcity;
    }

    public String getPzip() {
        return pzip;
    }

    public String getPstate() {
        return pstate;
    }

    public String getPphone() {
        return pphone;
    }

    public String getPaddress() {
        return paddress;
    }
}
