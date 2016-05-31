package com.cubic.smartdesk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 5;

	// Database Name
	private static final String DATABASE_NAME = "SmartDeskDatabase";

	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";

	// Contacts Table Columns names
	private static final String KEY_EMPLOYEE_ID = "employee_id";
	private static final String KEY_EMPLOYEE_FIRST_NAME = "firstname";
    private static final String KEY_EMPLOYEE_LAST_NAME = "lastname";
	private static final String KEY_EMPLOYEE_PH_NO = "phone_number";
	private static final String KEY_EMPLOYEE_EMAIL_ID = "email";

	// Appointments table name
	private static final String TABLE_APPOINTMENTS = "appointments";

	// Appointments Table Columns names
	private static final String KEY_GUEST_NAME = "guest_name";
	private static final String KEY_APPOINTMENT_DATE = "yymmdd";
	private static final String KEY_PURPOSE_OF_VISIT = "purpose_of_visit";

    private static DatabaseHandler sInstance;

    public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

    public static synchronized DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_EMPLOYEE_ID + " INTEGER PRIMARY KEY," + KEY_EMPLOYEE_FIRST_NAME + " TEXT,"
				+ KEY_EMPLOYEE_LAST_NAME + " TEXT," + KEY_EMPLOYEE_PH_NO + " TEXT," + KEY_EMPLOYEE_EMAIL_ID + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);

		String CREATE_APPOINTMENTS_TABLE = "CREATE TABLE " + TABLE_APPOINTMENTS + "("
				+ KEY_EMPLOYEE_ID + " INTEGER," + KEY_GUEST_NAME + " TEXT,"
				+ KEY_APPOINTMENT_DATE + " TEXT," + KEY_PURPOSE_OF_VISIT + " TEXT" + ")";
		db.execSQL(CREATE_APPOINTMENTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);

		// Create tables again
		onCreate(db);
	}

    // Adding new contact
	void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMPLOYEE_ID, contact.getID());
		values.put(KEY_EMPLOYEE_FIRST_NAME, contact.getFirstName()); // Employee Name
        values.put(KEY_EMPLOYEE_LAST_NAME, contact.getLastName()); // Employee Name
		values.put(KEY_EMPLOYEE_PH_NO, contact.getPhoneNumber()); // Employee Phone
		values.put(KEY_EMPLOYEE_EMAIL_ID, contact.getEmailId()); // Employee email id

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Retrieve single contact
	Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_EMPLOYEE_ID,
						KEY_EMPLOYEE_FIRST_NAME, KEY_EMPLOYEE_LAST_NAME, KEY_EMPLOYEE_PH_NO, KEY_EMPLOYEE_EMAIL_ID }, KEY_EMPLOYEE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
			// return contact
			return contact;
		}
		else
			return null;
	}

	// Retrieve single contact
	Contact getContact(String empName) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_EMPLOYEE_ID,
						KEY_EMPLOYEE_FIRST_NAME, KEY_EMPLOYEE_LAST_NAME, KEY_EMPLOYEE_PH_NO, KEY_EMPLOYEE_EMAIL_ID }, KEY_EMPLOYEE_FIRST_NAME + "=?",
				new String[] { String.valueOf(empName) }, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();

			Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

			return contact;
		}
		else
			return null;
	}

	// Retrieve all Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
				contact.setPhoneNumber(cursor.getString(3));
                contact.setEmailId(cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Update single contact
	public int updateContact(Contact contact) {
		/*SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMPLOYEE_NAME, contact.getName());
		values.put(KEY_EMPLOYEE_PH_NO, contact.getPhoneNumber());
		values.put(KEY_EMPLOYEE_EMAIL_ID, contact.getEmailId());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_EMPLOYEE_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });*/
        return 0;
	}

	// Delete single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_EMPLOYEE_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}


	// Get contacts database Count


	/*************************************** Appointments Table *************************************/

	// Adding new Appointment
	void addAppointment(Appointment appt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMPLOYEE_ID, appt.getID()); // Employee Id
		values.put(KEY_GUEST_NAME, appt.getName()); // Guest Name
		values.put(KEY_APPOINTMENT_DATE, appt.getAppointmentDate().toString()); // Appointment Date
		values.put(KEY_PURPOSE_OF_VISIT, appt.getPurposeOfVisit()); // Appointment Date

		// Inserting Row
		db.insert(TABLE_APPOINTMENTS, null, values);
		db.close(); // Closing database connection
	}

	// Retrieve Appointment
	Appointment getAppointment(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_APPOINTMENTS, new String[] { KEY_EMPLOYEE_ID,
						KEY_GUEST_NAME, KEY_APPOINTMENT_DATE, KEY_PURPOSE_OF_VISIT }, KEY_EMPLOYEE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Appointment Appt = new Appointment(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		// return contact
		return Appt;
	}

	// Retrieve Appointment
	Appointment getAppointment(String guestName) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_APPOINTMENTS, new String[] { KEY_EMPLOYEE_ID,
						KEY_GUEST_NAME, KEY_APPOINTMENT_DATE, KEY_PURPOSE_OF_VISIT }, KEY_GUEST_NAME + "=?",
				new String[] { String.valueOf(guestName) }, null, null, null, null);
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToFirst();

			Appointment Appt = new Appointment(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2), cursor.getString(3));
			// return contact
			return Appt;
		}
		else
			return null;
	}


	// Retrieve all Contacts
	public List<Appointment> getAllAppointments() {
		List<Appointment> apptList = new ArrayList<Appointment>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_APPOINTMENTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Appointment appt = new Appointment();
				appt.setID(Integer.parseInt(cursor.getString(0)));
				appt.setName(cursor.getString(1));
				appt.setAppointmentDate(cursor.getString(2));
				appt.setPurposeOfVisit(cursor.getString(3));
				// Adding contact to list
				apptList.add(appt);
			} while (cursor.moveToNext());
		}

		// return contact list
		return apptList;
	}

	private Date ConvertToDate(String S)
	{
		String dateString = "03/26/2012 11:49:00 AM";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;
	}

	// Retrieve all Contacts

	// Update single contact
	public int updateAppointment(Appointment appt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMPLOYEE_ID, appt.getID()); // Employee Id
		values.put(KEY_GUEST_NAME, appt.getName()); // Guest Name
		values.put(KEY_APPOINTMENT_DATE, appt.getAppointmentDate().toString()); // Appointment Date
		values.put(KEY_PURPOSE_OF_VISIT, appt.getPurposeOfVisit()); // Purpose Of Visit

		// updating row
		return db.update(TABLE_APPOINTMENTS, values, KEY_EMPLOYEE_ID + " = ?",
				new String[] { String.valueOf(appt.getID()) });
	}

	// Delete single contact
	public void deleteAppointment(Appointment appt) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_APPOINTMENTS, KEY_EMPLOYEE_ID + " = ?",
				new String[] { String.valueOf(appt.getID()) });
		db.close();
	}


	// Get contacts database Count


}
