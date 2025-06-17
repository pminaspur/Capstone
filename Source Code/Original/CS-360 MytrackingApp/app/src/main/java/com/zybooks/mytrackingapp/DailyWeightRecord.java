
// Daily Weight Record  : This Class is created to keep record of the daily weight details of the user.
// //Modified By : Pushpa Laxman
//  Modified Date: 12/14/2024

package com.zybooks.mytrackingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class DailyWeightRecord extends AppCompatActivity {
    public MyTrackingAppDBHelper dbHelper;
    private EditText textDate;
    private EditText textDailyWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_weight_record);
        Intent intent = getIntent();
        String UserName = intent.getStringExtra("logged_user_name");
        String selected_weight_Id = intent.getStringExtra("selected_weight_Id");
        String selected_weight_date = intent.getStringExtra("selected_weight_date");
        String selected_weight = intent.getStringExtra("selected_weight");
        // add/update/delete buttons created
        Button add_weight_button=findViewById(R.id.btn_add_weight);
        Button update_weight_button =findViewById(R.id.btn_update_weight);
        Button delete_weight_button =findViewById(R.id.btn_del_weight);
        // text created for the user to add date and daily weight
        textDate = (EditText)findViewById(R.id.txt_date);
        textDailyWeight = (EditText)findViewById(R.id.txt_weight);
         //if  records found in the list
        if(selected_weight_Id == "" || selected_weight_Id == null )
        {
            // update the selected record
            update_weight_button.setVisibility(View.INVISIBLE);
            //delete the record selected
            delete_weight_button.setVisibility(View.INVISIBLE);
        }
        else
        {
            //add  the new record
            add_weight_button.setVisibility(View.INVISIBLE);
            textDate.setText(selected_weight_date);
            textDailyWeight.setText(selected_weight);
        }
        // adding the details in the database
        dbHelper = new MyTrackingAppDBHelper(this);
        // click on the add/update to enter the weight and the date
        add_weight_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Weightdate = textDate.getText().toString();
                String Weightrecord = textDailyWeight.getText().toString();
                long WeightId = dbHelper.addUserDailyWeight(Weightdate, Float.valueOf(Weightrecord));
                Toast.makeText(DailyWeightRecord.this,
                                "Successfully Added Weight - "+WeightId,
                                Toast.LENGTH_SHORT)
                        .show();
                //finish();
                Intent intent = new Intent(DailyWeightRecord.this, weight_details.class);
                intent.putExtra("logged_user_name", UserName);
                startActivity(intent);
            }
        });

        // click on the update button for changing the old weight and date
        update_weight_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Weightdate = textDate.getText().toString();
                String Weightrecord = textDailyWeight.getText().toString();
                int record_affected = dbHelper.updateUserDailyWeight(Long.parseLong(selected_weight_Id),Weightdate, Float.valueOf(Weightrecord));
                Toast.makeText(DailyWeightRecord.this,
                                "Successfully Updated Weight",
                                Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(DailyWeightRecord.this, weight_details.class);
                intent.putExtra("logged_user_name", UserName);
                startActivity(intent);
            }
        });

        //click on the delete button to delete the selected record
        delete_weight_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //long WeightId = 1;
                int record_affected = dbHelper.deleteUserDailyWeight(Long.parseLong(selected_weight_Id));
                Toast.makeText(DailyWeightRecord.this,
                                "Successfully Deleted Weight",
                                Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(DailyWeightRecord.this, weight_details.class);
                intent.putExtra("logged_user_name", UserName);
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