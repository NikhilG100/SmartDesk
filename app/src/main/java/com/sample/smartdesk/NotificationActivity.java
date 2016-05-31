package com.cubic.smartdesk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationActivity extends Activity {

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setImageResource(R.drawable.onecubicwhite);

        Bundle extras = getIntent().getExtras();
        String inputString = extras.getString("Guest_Name");
        String employeeFirstNameString = extras.getString("Employee_First_Name");
        String employeeLastNameString = extras.getString("Employee_Last_Name");

        if (inputString != null && employeeFirstNameString != null && employeeLastNameString != null) {
            TextView tvHello = (TextView) this.findViewById(R.id.textViewGuestName);
            tvHello.setText("Hi " + inputString + "!");

            TextView tvEmployee = (TextView) this.findViewById(R.id.textViewEmployeeName);
            tvEmployee.setText(employeeFirstNameString + " " + employeeLastNameString + " has been notified.");
        }

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }, 5000 /*amount of time in milliseconds before execution*/ );


    }

    @Override
    public void onBackPressed() {
    }
}
