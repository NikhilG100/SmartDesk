package com.cubic.smartdesk;

import java.util.Date;

/**
 * Created by gandhin on 5/11/2016.
 */
public class Appointment {
    //private variables
    int employeeId;
    String guestName;
    String apptDate;
    String purposeOfVisit;

    // Empty constructor
    public Appointment(){

    }

    // constructor
    public Appointment(int id, String name, String aptDate, String purposeOfVisit){
        this.employeeId = id;
        this.guestName = name;
        this.apptDate = aptDate;
        this.purposeOfVisit = purposeOfVisit;
    }

    //Get employee ID
    public int getID(){
        return this.employeeId;
    }

    //Set employee id
    public void setID(int id){
        this.employeeId = id;
    }

    //Get guest name
    public String getName(){
        return this.guestName;
    }

    //Set guest name
    public void setName(String name){
        this.guestName = name;
    }

    //Get Date/Time of appointment
    public String getAppointmentDate(){
        return this.apptDate;
    }

    //Set Date/Time of appointment
    public void setAppointmentDate(String aDate){
        this.apptDate = aDate;
    }

    //Get purpose of visit
    public String getPurposeOfVisit(){
        return this.purposeOfVisit;
    }

    //Set purpose of visit
    public void setPurposeOfVisit(String purpose){
        this.purposeOfVisit = purpose;
    }
}
