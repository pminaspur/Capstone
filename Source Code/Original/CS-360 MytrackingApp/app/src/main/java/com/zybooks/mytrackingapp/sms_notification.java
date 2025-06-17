
// SMS NOTIFICATION : This Class is created to send SMS Notification to the user on reaching his target goal.
// //Modified By : Pushpa Laxman
//  Modified Date: 12/14/2024
package com.zybooks.mytrackingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class sms_notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sms_notification);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onButtonClick(View v) {
         // check if the user has not granted permission  for sending the sms notification about reaching the target goal
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        } else {
            //if permission granted then send sms notification
            sendSMS();
        }
    }

    private void sendSMS() {

        Intent intent = getIntent();
        String UserName = intent.getStringExtra("logged_user_name");

        EditText phoneText = findViewById(R.id.editTextPhone);
        String phoneNumber = String.valueOf(phoneText.getText());

        //notify about reaching target goal to the user
        String message = "Target Weight Achieved : " + phoneNumber;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS Sent to: " + phoneNumber, Toast.LENGTH_SHORT).show();
            intent = new Intent(sms_notification.this, weight_details.class);
            intent.putExtra("logged_user_name", UserName);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "SMS Failed to Send", Toast.LENGTH_SHORT).show();
        }
    }
}