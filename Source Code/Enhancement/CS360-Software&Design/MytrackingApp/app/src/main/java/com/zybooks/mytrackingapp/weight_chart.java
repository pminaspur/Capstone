package com.zybooks.mytrackingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import java.util.ArrayList;

public class weight_chart extends AppCompatActivity {

    // Variable for the bar chart
    BarChart barChart;

    // Variables for bar data sets
    BarDataSet barDataSet1, barDataSet2;

    // ArrayList for storing entries
    ArrayList<BarEntry> barEntries;

    // Creating a string array for displaying days
    String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    ArrayList<WeightModel> AllWeightRecords;
    public MyTrackingAppDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_chart);

        Intent currentintent = getIntent();
        String str_logged_user_name = currentintent.getStringExtra("logged_user_name");

        dbHelper = new MyTrackingAppDBHelper(this);
        AllWeightRecords = dbHelper.getAllWeightDetails();

        // Initializing the variable for the bar chart
        barChart = findViewById(R.id.idBarChart);

        // Creating a new bar data set getBarEntriesUserWeight
        barDataSet1 = new BarDataSet(getBarEntriesUserWeight(AllWeightRecords), "User Daily Weights");
        barDataSet1.setColor(Color.GREEN);

        // Adding bar data sets to the bar data
        BarData data = new BarData(barDataSet1);

        // Setting the data to the bar chart
        barChart.setData(data);

        // Removing the description label of the bar chart
        barChart.getDescription().setEnabled(false);

        // Getting the x-axis of the bar chart
        XAxis xAxis = barChart.getXAxis();

        // Setting value formatter to the x-axis
        // and adding the days to the x-axis labels
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // Setting center axis labels for the bar chart
        xAxis.setCenterAxisLabels(true);

        // Setting the position of the x-axis to bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Setting granularity for the x-axis labels
        xAxis.setGranularity(1);

        // Enabling granularity for the x-axis
        xAxis.setGranularityEnabled(true);

        // Making the bar chart draggable
        barChart.setDragEnabled(true);

        // Setting the visible range for the bar chart
        barChart.setVisibleXRangeMaximum(3);

        // Setting the width of the bars
        data.setBarWidth(0.15f);

        // Setting the minimum axis value for the chart
        barChart.getXAxis().setAxisMinimum(0);

        // Animating the chart
        barChart.animate();

        // Invalidating the bar chart
        barChart.invalidate();


        Button back_button=findViewById(R.id.back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weight_chart.this, weight_details.class);
                intent.putExtra("logged_user_name", str_logged_user_name);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private ArrayList<BarEntry> getBarEntriesUserWeight(ArrayList<WeightModel> AllUserWeightsRecords) {
        // Creating a new ArrayList
        barEntries = new ArrayList<>();
        int start = 1;

        for (WeightModel weight:  AllUserWeightsRecords) {
            float mystart = (float) start;
            barEntries.add(new BarEntry(mystart, weight.getDaily_weight()));
            start = start + 1;
        }

        return barEntries;
    }
}