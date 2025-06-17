

// Weight Details : This Class is created for the second screen to add/update/delete user weight details along with the date.
// //Modified By : Pushpa Laxman
//  Modified Date: 12/14/2024


package com.zybooks.mytrackingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class weight_details extends AppCompatActivity {

    ArrayList<WeightModel> AllWeightRecords;
    public MyTrackingAppDBHelper dbHelper;

    public GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_details);

        TextView logged_user_name = (TextView)findViewById(R.id.txt_logged_user);
        Intent intent = getIntent();
        String str_logged_user_name = intent.getStringExtra("logged_user_name");
        logged_user_name.setText("Welcome - "+str_logged_user_name);

        dbHelper = new MyTrackingAppDBHelper(this);
        AllWeightRecords = dbHelper.getAllWeightDetails();

        gridview  = (GridView)findViewById(R.id.user_weight_details);
        CustomAdapter customAdp = new CustomAdapter();
        gridview.setAdapter(customAdp);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WeightModel current_record = AllWeightRecords.get(position);

                Intent intent = new Intent(weight_details.this, DailyWeightRecord.class);
                intent.putExtra("logged_user_name", str_logged_user_name);
                intent.putExtra("selected_weight_Id",  String.valueOf(current_record.getWeightId()));
                intent.putExtra("selected_weight_date",  current_record.getDate());
                intent.putExtra("selected_weight", String.valueOf(current_record.getDaily_weight()));
                startActivity(intent);
            }
        });

        Button save_button=findViewById(R.id.saveWeight);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weight_details.this, DailyWeightRecord.class);
                intent.putExtra("logged_user_name", str_logged_user_name);
                startActivity(intent);
            }
        });

        Button set_notifybtn=findViewById(R.id.setnotfication);
        set_notifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weight_details.this, sms_notification.class);
                intent.putExtra("logged_user_name", str_logged_user_name);
                startActivity(intent);
            }
        });

        ImageButton target_btn =findViewById(R.id.editTargetWeight_button);
        target_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView target_weight = (TextView)findViewById(R.id.goalWeightText);
                String Weightrecord = target_weight.getText().toString();
                dbHelper.UserWeightTarget(Float.valueOf(Weightrecord));
                Toast.makeText(weight_details.this,
                                "Target Weight Successfully Setup - "+Weightrecord,
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return AllWeightRecords.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.view_weight_record,null);
            TextView user_weight_id = view1.findViewById(R.id.weight_id);
            TextView user_weight_date = view1.findViewById(R.id.weight_date);
            TextView user_weight_record = view1.findViewById(R.id.weight_record);
            WeightModel current_record = AllWeightRecords.get(position);
            user_weight_id.setText(String.valueOf(current_record.getWeightId()));
            user_weight_date.setText(current_record.getDate());
            user_weight_record.setText(String.valueOf(current_record.getDaily_weight()));
            return view1;
        }
    }
}