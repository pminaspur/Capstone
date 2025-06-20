
// Main activity  : This Class is created for the login screen.
// //Modified By : Pushpa Laxman
//  Modified Date: 12/14/2024

package com.zybooks.mytrackingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public MyTrackingAppDBHelper dbHelper;
    private EditText textUserName;
    private EditText textUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        dbHelper = new MyTrackingAppDBHelper(this);
        // user can add the username
        textUserName = (EditText)findViewById(R.id.username);
        //user can add the password
        textUserPassword = (EditText)findViewById(R.id.password);
        // user should click on the button to enter the app
        Button login_button=findViewById(R.id.loginbutton);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName = textUserName.getText().toString();
                String UserPassword = textUserPassword.getText().toString();
                //validating the user credentials
                boolean isValid = dbHelper.validateUserLogin(UserName,UserPassword);
                if(!isValid)
                {   // if user has added incorrect credentials then notifying them with the error message
                    Toast.makeText(MainActivity.this,
                                    "Invalid Login Details, Please Try Again with correct details",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, weight_details.class);
                    intent.putExtra("logged_user_name", UserName);
                    startActivity(intent);
                }
            }
        });
        // new user should click on the create account button
        Button new_account=findViewById(R.id.createaccountbutton);
        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String UserName = textUserName.getText().toString();
                //String UserPassword = textUserPassword.getText().toString();
                //long userID = dbHelper.addUserLogin(UserName,UserPassword);
                //new user created an account and hence a message will populate upon login details added successfully
                //Toast.makeText(MainActivity.this,
                 //               "Login details added successfully, Please Login"
                  //                      + UserName,
                  //              Toast.LENGTH_SHORT)
                  //      .show();
                Intent intent = new Intent(MainActivity.this, create_new_account.class);
                //Intent intent = new Intent(MainActivity.this, weight_chart.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}