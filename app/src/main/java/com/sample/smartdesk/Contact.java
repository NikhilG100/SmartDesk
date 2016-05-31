package com.cubic.smartdesk;

public class Contact {
	
	//private variables
	int employeeId;
	String employeeFirstName;
	String employeeLastName;
	String employeePhoneNumber;
	String employeeEmailId;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String fName, String lName, String phoneNumber, String emailId){
		this.employeeId = id;
		this.employeeFirstName = fName;
		this.employeeLastName = lName;
		this.employeePhoneNumber = phoneNumber;
		this.employeeEmailId = emailId;
	}
	
	//Get employee ID
	public int getID(){
		return this.employeeId;
	}
	
	//Set employee id
	public void setID(int id){
		this.employeeId = id;
	}
	
	//Get employee first name
	public String getFirstName(){
		return this.employeeFirstName;
	}
	
	//Set employee first name
	public void setFirstName(String fName){
		this.employeeFirstName = fName;
	}

	//Get employee last name
	public String getLastName(){
		return this.employeeLastName;
	}

	//Set employee first name
	public void setLastName(String lName){
		this.employeeLastName = lName;
	}
	
	//Get employee phone number
	public String getPhoneNumber(){
		return this.employeePhoneNumber;
	}
	
	//Set employee phone number
	public void setPhoneNumber(String phoneNumber){
		this.employeePhoneNumber = phoneNumber;
	}

    //Get employee email id
    public String getEmailId(){
        return this.employeeEmailId;
    }

    //Set employee email id
    public void setEmailId(String emailId){
        this.employeePhoneNumber = emailId;
    }

}
