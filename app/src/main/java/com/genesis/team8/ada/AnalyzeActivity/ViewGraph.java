package com.genesis.team8.ada.AnalyzeActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.genesis.team8.ada.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by asif ali on 14/01/17.
 */

public class ViewGraph extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygraph);
        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getxvals(),getdataset());

        chart.setData(data);
        chart.setDescription("Accident Analysis");
        chart.animateXY(4000,4000);
        chart.invalidate();
        

    }

    private ArrayList<BarDataSet> getdataset() {

        ArrayList<BarDataSet> dsets = null;

        ArrayList<BarEntry> values = new ArrayList<>();
        Random ran = new Random();
        int x = ran.nextInt(10) + 10;


        BarEntry v1 = new BarEntry(x,0);
        values.add(v1);
        x = ran.nextInt(10) + 10;
        BarEntry v2 = new BarEntry(x,1);
        values.add(v2);
        x = ran.nextInt(100) + 10;
        BarEntry v3 = new BarEntry(x,2);
        values.add(v3);
        x = ran.nextInt(100) + 10;
        BarEntry v4 = new BarEntry(x,3);
        values.add(v4);
        x = ran.nextInt(100) + 10;
        BarEntry v5 = new BarEntry(x,4);
        values.add(v5);
        x = ran.nextInt(100) + 10;
        BarEntry v6 = new BarEntry(x,5);
        values.add(v6);

        BarDataSet ds = new BarDataSet(values,"Number of Accidents");
        ds.setColors(ColorTemplate.COLORFUL_COLORS);

        dsets = new ArrayList<>();
        dsets.add(ds);

        return dsets;


    }

    private ArrayList<String> getxvals() {
    
        ArrayList<String> x = new ArrayList<>();
        
        x.add("00-04");
        x.add("04-08");
        x.add("08-12");
        x.add("12-16");
        x.add("16-20");
        x.add("20-24");
        
        return x;
               
        
    }
    


}
