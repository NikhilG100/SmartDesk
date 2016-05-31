package com.cubic.smartdesk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VisitorActivity extends Activity {

    ImageView imgView;
    EditText employeeNameEditText;
    Button meetingButton;
    Button salesButton;
    Button personalVisitButton;
    Button deliveryButton;
    Button interviewButton;
    Button otherButton;
    DatabaseHandler db;
    String inputString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageResource(R.drawable.onecubicwhite);

        Bundle extras = getIntent().getExtras();
        inputString = extras.getString("Guest_Name");

        TextView tvHello = (TextView) this.findViewById(R.id.textViewHello);
        tvHello.setText("Hi "+ inputString + "!");

        employeeNameEditText = (EditText) this.findViewById(R.id.editText);

        meetingButton = (Button) this.findViewById(R.id.buttonMeeting);
        personalVisitButton = (Button) this.findViewById(R.id.buttonPersonalVisit);
        salesButton = (Button) this.findViewById(R.id.buttonSales);
        deliveryButton = (Button) this.findViewById(R.id.buttonDelivery);
        interviewButton = (Button) this.findViewById(R.id.buttonInterview);
        otherButton = (Button) this.findViewById(R.id.buttonOther);

        db = DatabaseHandler.getInstance(getApplicationContext());

        meetingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                   AddAppointmentSendEmail("Meeting", v);
                }
        });

        personalVisitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddAppointmentSendEmail("Personal Visit", v);
            }

        });

        salesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddAppointmentSendEmail("Sales", v);
            }

        });

        deliveryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddAppointmentSendEmail("Delivery", v);
            }
        });

        interviewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddAppointmentSendEmail("Interview", v);
            }

        });

        otherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddAppointmentSendEmail("Other", v);
            }
        });
    }

    @Override
    public void onBackPressed() {
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


    void AddAppointmentSendEmail(String apptStr, View v) {
        String employeeName = employeeNameEditText.getText().toString();

        if (!employeeName.isEmpty()) {
            employeeName = employeeName.substring(0, 1).toUpperCase() + employeeName.substring(1);
            Contact empContact = db.getContact(employeeName);

            if (empContact != null) {
                Date d = new Date();
                String s  = DateFormat.format("MM/dd/yyyy HH:mm:ss a", d.getTime()).toString();
                //Appointment appt = new Appointment(empContact.getID(), inputString, s,apptStr);

                //Log.d("Adding","Adding new appointment...");
                //db.addAppointment(appt);

                SendEmailToEmployee(inputString, empContact);

                Intent intent = new Intent(v.getContext(), NotificationActivity.class);
                intent.putExtra("Guest_Name", inputString);
                intent.putExtra("Employee_First_Name", empContact.getFirstName());
                intent.putExtra("Employee_Last_Name", empContact.getLastName());
                v.getContext().startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(VisitorActivity.this,"No Employee Found. Please Try Again.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        else {
            Toast toast = Toast.makeText(VisitorActivity.this,"Please Enter Employee Name.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        /*Log.d("Reading: ", "Reading all appointments...");
        List<Appointment> appts = db.getAllAppointments();

        for (Appointment appt : appts) {
            String log = "Id: " + appt.getID() + " ,Name: " + appt.getName() + " ,Phone: " + appt.getAppointmentDate().toString();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }*/
    }
}
