package com.zybooks.mytrackingapp;

import static java.lang.Integer.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class create_new_account extends AppCompatActivity {

    public MyTrackingAppDBHelper dbHelper;
    private EditText textUserName;
    private EditText textUserPassword;
    private EditText textEmailAddress;
    private EditText textAge;
    private EditText textHeight;

    boolean IsDataValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_account);

        dbHelper = new MyTrackingAppDBHelper(this);
        // user can add the username
        textUserName = (EditText)findViewById(R.id.username);
        //user can add the password
        textUserPassword = (EditText)findViewById(R.id.password);
        //user can add the Email Address
        textEmailAddress = (EditText)findViewById(R.id.emailaddress);
        //user can add Age
        textAge = (EditText)findViewById(R.id.age);
        //user can add Height
        textHeight = (EditText)findViewById(R.id.height);

        // new user should click on the create account button
        Button new_account=findViewById(R.id.createaccountbutton);
        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserName = textUserName.getText().toString();
                String UserPassword = textUserPassword.getText().toString();
                String EmailAddress = textEmailAddress.getText().toString();
                String Age = textAge.getText().toString();
                String Height = textHeight.getText().toString();

                IsDataValid = ValidateForm(UserName,UserPassword, EmailAddress, Age, Height);

                if(IsDataValid){

                    long accountID = dbHelper.addAccount(UserName,EmailAddress,Age,Height);

                    if(accountID > 0)
                    {
                        long userId = dbHelper.addUserLogin(UserName, UserPassword);

                        //new user created an account and hence a message will populate upon login details added successfully
                        Toast.makeText(create_new_account.this,
                                "Account created successfully, Please Login"
                                        + UserName,
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(create_new_account.this, MainActivity.class );
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(create_new_account.this,
                                "Account creation failed, Please try again",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean ValidateForm(String userName, String userPassword, String emailAddress, String age, String height){

        if(userName.isEmpty()){
            textUserName.setError("UserName is mandatory");
            return false;
        }

        if(userPassword.isEmpty()){
            textUserPassword.setError("UserPassword is mandatory");
            return false;
        }

        if(emailAddress.isEmpty()){
            textEmailAddress.setError("EmailAddress is mandatory");
            return false;
        }
        //else {
        //    if (Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
        //       textEmailAddress.setError("Enter valid EmailAddress");
        //        return false;
        //    }
        //}

        if(age.isEmpty()){
            textAge.setError("Age is mandatory");
            return false;
        }
        /*else {
            int userAge = Integer.parseInt(age);
            if(userAge >= 18 && userAge <= 70){
                textAge.setError("Enter valid Age");
                return false;
            }
        }*/

        if (height.isEmpty()) {
            textHeight.setError("Height is mandatory");
            return false;
        }
       /* else {
            int userHeight = Integer.parseInt(height);
            if(userHeight >= 10 && userHeight <= 30){
                textHeight.setError("Enter valid height");
                return false;
            }
        }*/

        return  true;
    }
}