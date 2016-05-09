package com.example.brianscott.caresit;

/**
 * Created by Brian Scott on 4/25/2016.
 */
public class Request
{
    private String startTimeAndDate;
    private String Length;
    private String numKids;
    private String provider;
    private String user;
    private String description;
    private String completed;
    private int cost;

    public String getCompleted()
    {
        return completed;
    }

    public void setCompleted(String completed)
    {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTimeAndDate()
    {
        return startTimeAndDate;
    }

    public void setStartTimeAndDate(String startTimeAndDate)
    {
        this.startTimeAndDate = startTimeAndDate;
    }

    public String getLength()
    {
        return Length;
    }

    public void setLength(String length)
    {
        Length = length;
    }

    public String getNumKids()
    {
        return numKids;
    }

    public void setNumKids(String numKids)
    {
        this.numKids = numKids;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Request()
    {
        this.startTimeAndDate = "";
        this.numKids = "";
        this.Length = "";
        this.description = "";
        this.provider = "";
        this.user = "";
        this.completed ="";
        this.cost = -1;
    }
}
