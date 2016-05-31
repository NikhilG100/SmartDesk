package com.cubic.smartdesk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    EditText editTextGuestName;
    EditText editTextGuestEmail;
    Button buttonSignIn;
    String guestName;
    ImageView imgView;
    Contact empContact;
    Appointment guestAppt = null;
    String guestEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) this.findViewById(R.id.WelcomeTextView);
        tv.setSelected(true);  // Set focus to the text view

        editTextGuestName = (EditText) findViewById(R.id.editTextGuestName);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        editTextGuestEmail = (EditText) findViewById(R.id.editTextGuestEmail);

        imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageResource(R.drawable.onecubicwhite);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String str = editTextName.getText().toString();
                guestName = editTextGuestName.getText().toString();

                guestEmail = editTextGuestEmail.getText().toString();

                DatabaseHandler db = DatabaseHandler.getInstance(v.getContext());

                if (guestName.isEmpty() || guestEmail.isEmpty())
                {
                    Toast toast = Toast.makeText(MainActivity.this,"Name Or Email Is Empty.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                guestName = guestName.substring(0, 1).toUpperCase() + guestName.substring(1);

                if (guestName.toString().equals("Insert"))
                {
                    // Inserting Contacts
                    Log.d("Insert: ", "Inserting into employee database...");
                    db.addContact(new Contact(11356, "Matt", "Cole", "6195546271", "matthew.cole@cubic.com"));
                    db.addContact(new Contact(7439, "Nikhil", "Gandhi", "6198542300", "nikhil.gandhi@cubic.com"));
                    db.addContact(new Contact(4422, "Hardeep", "Singh", "6196173286", "hardeep.singh@cubic.com"));
                    db.addContact(new Contact(5477, "Pradip", "Mistry", "6195444569", "pradip.mistry@cubic.com"));
                    db.addContact(new Contact(6289, "Angela", "Miller", "6195278496", "angela.miller@cubic.com"));
                    db.addContact(new Contact(8579, "Katie", "Nahas", "6193826734", "katie.nahas@cubic.com"));
                    db.addContact(new Contact(6472, "Mark", "Hedstorm", "6193684562", "hedstorm@cubic.com"));
                    db.addContact(new Contact(8581, "Joshua", "List", "6195658474", "joshua@cubic.com"));
                    db.addContact(new Contact(20934, "Steve", "Caro", "6196144436", "james@cubic.com"));
                    db.addContact(new Contact(2568, "Ernie", "Melendez", "6194234530", "kevin@cubic.com"));
                    db.addContact(new Contact(4885, "Veronica", "Haynes", "6196174417", "haynes@cubic.com"));
                    db.addContact(new Contact(5236, "Nirlabh", "Singh", "6196175225", "nirlabh@cubic.com"));
                    db.addContact(new Contact(7942, "Mark", "Mendoza", "6196174337", "mark@cubic.com"));
                    db.addContact(new Contact(7845, "Mark", "Harris", "6198234569", "harris@cubic.com"));
                    db.addContact(new Contact(9378, "Valerie", "Gibson", "6197338752", "valerie@cubic.com"));

                    // Reading all contacts
                    Log.d("Reading: ", "Reading all contacts..");
                    List<Contact> contacts = db.getAllContacts();

                    for (Contact cn : contacts) {
                        String log = "Id: " + cn.getID() + " ,First Name: " + cn.getFirstName() +  " ,Last Name: " + cn.getLastName() + " ,Phone: " + cn.getPhoneNumber();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                    }

                    // Inserting Appointments
                    Log.d("Insert: ", "Inserting into appointments database...");
                    db.addAppointment(new Appointment(20934, "Michael", "05/10/2016 11:00:00 AM", "Other"));
                    db.addAppointment(new Appointment(11356, "Katie", "05/12/2016 3:30:00 PM", "Delivery"));
                    db.addAppointment(new Appointment(5477, "Phil", "05/16/2016 2:00:00 PM", "Personal Visit"));
                    db.addAppointment(new Appointment(6835, "Mark", "05/17/2016 2:00:00 PM", "Personal Visit"));
                    db.addAppointment(new Appointment(4422, "Susan", "05/18/2016 4:00:00 PM", "Meeting"));
                    db.addAppointment(new Appointment(20934, "Paul", "05/18/2016 3:30:00 PM", "Meeting"));
                    db.addAppointment(new Appointment(6472, "Anne", "05/14/2016 2:30:00 PM", "Personal Visit"));
                    db.addAppointment(new Appointment(6289, "Kim", "05/14/2016 2:30:00 PM", "Personal Visit"));
                    db.addAppointment(new Appointment(7845, "Steve", "05/14/2016 3:30:00 PM", "Delivery"));
                    db.addAppointment(new Appointment(8579, "Ted", "05/14/2016 3:30:00 PM", "Delivery"));

                    // Reading all contacts
                    Log.d("Reading: ", "Reading all appointments...");
                    List<Appointment> appts = db.getAllAppointments();

                    for (Appointment appt : appts) {
                        String log = "Id: " + appt.getID() + " ,Name: " + appt.getName() + " ,Phone: " + appt.getAppointmentDate().toString();
                        // Writing Contacts to log
                        Log.d("Name: ", log);
                    }
                }
                else {
                    guestAppt = db.getAppointment(guestName);
                    if (guestAppt != null) {

                        String log = "Id: " + guestAppt.getID() + " ,Name: " + guestAppt.getName() + " ,Date: " + guestAppt.getAppointmentDate();
                        Log.d("Appointment: ", log);

                        empContact = db.getContact(guestAppt.getID());

                        if (empContact != null) {
                            String log1 = "Id: " + empContact.getID() + " , First Name: " + empContact.getFirstName() + " ,Date: " + empContact.getEmailId();
                            Log.d("Employee: ", log1);

                            SendEmailToEmployee(guestName, empContact);

                            SendEmailToGuest(guestName, guestEmail);

                            Intent intent = new Intent(v.getContext(), NotificationActivity.class);
                            intent.putExtra("Guest_Name", guestName);
                            intent.putExtra("Employee_First_Name", empContact.getFirstName());
                            intent.putExtra("Employee_Last_Name", empContact.getLastName());

                            v.getContext().startActivity(intent);
                        }
                    } else {
                        SendEmailToGuest(guestName, guestEmail);

                        Intent intent = new Intent(v.getContext(), VisitorActivity.class);
                        intent.putExtra("Guest_Name", guestName);

                        v.getContext().startActivity(intent);
                    }
                }
            }
        });
    }

    void SendEmailToEmployee(final String gName, final Contact employeeContact)
    {
            Log.d("Email: ","Sending email to Employee start.... ");
            final GMailSender sender = new GMailSender("onecubic2016@gmail.com", "Cubic2016");
            new AsyncTask<Void, Void, Void>() {
                @Override public Void doInBackground(Void... arg) {
                    try {
                        sender.sendMail("Your Guest is here",
                                "Hi " + employeeContact.getFirstName() + ",\n\n" + gName + " is waiting for you in lobby." + "\n\n Thanks,\n SmartDesk",
                                "onecubic2016@gmail.com",
                                employeeContact.getEmailId());
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                    return null;
                }

                protected void onPostExecute(Long result) {
                    try {
                        Log.e("onPostExecute", "onPostExecute");

                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }
            }.execute();

    }

    void SendEmailToGuest(final String gName, final String gEmail)
    {
        Log.d("Email: ","Sending feedback email to Guest start.... ");
        final GMailSender sender = new GMailSender("onecubic2016@gmail.com", "Cubic2016");
        new AsyncTask<Void, Void, Void>() {
            @Override public Void doInBackground(Void... arg) {
                try {
                    sender.sendMail("Thank You For Visiting Cubic !",
                            "Hi " + gName + ",\n\n" + "Thank you for your recent visit to Cubic." + "\n\n" +
                                    "Please fill out short survey about your experience -- \n\n" +
                                    "https://www.surveymonkey.com/r/FXK68NK" + "\n\n Thanks,\n SmartDesk",
                            "onecubic2016@gmail.com",
                            gEmail);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
                return null;
            }

            protected void onPostExecute(Long result) {
                try {
                    Log.e("onPostExecute", "onPostExecute");

                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        }.execute();

    }
}
