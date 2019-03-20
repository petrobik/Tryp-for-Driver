package com.rdev.trypfordriver.ui.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.views.EarningMarkerView;
import com.rdev.trypfordriver.views.MilesMarkerView;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private Spinner mEarningSpinner;
    private Spinner mMilesSpinner;
    private LineChart mEarningChart;
    private LineChart mMilesChart;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
//            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                w.setStatusBarColor(Color.TRANSPARENT);
            }
        }

        super.onCreate(savedInstanceState);

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }

        setContentView(R.layout.activity_report);

        mEarningSpinner = findViewById(R.id.earning_spinner);
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this, R.layout.spinner_item, getResources().getStringArray(R.array.spinner_list));
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        mEarningSpinner.setAdapter(spinnerAdapter);

        mMilesSpinner = findViewById(R.id.miles_spinner);
        mMilesSpinner.setAdapter(spinnerAdapter);

        mEarningChart = findViewById(R.id.earning_chart);
        mMilesChart = findViewById(R.id.miles_chart);

        initEarningChart();
        initMilesChart();

        mEarningChart.invalidate();
        mMilesChart.invalidate();

        mFloatingActionButton = findViewById(R.id.screen28_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initEarningChart() {
        mEarningChart.setTouchEnabled(true);
        mEarningChart.setAutoScaleMinMaxEnabled(true);
        mEarningChart.getDescription().setEnabled(false);
        mEarningChart.setPinchZoom(false);
        mEarningChart.setScaleYEnabled(false);
        mEarningChart.setScaleXEnabled(false);
        mEarningChart.setExtraBottomOffset(20);

        YAxis yAxisRight = mEarningChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxis = mEarningChart.getAxisLeft();
        yAxis.setEnabled(false);
        XAxis xAxis = mEarningChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        yAxis.setAxisMinimum(20);
        xAxis.setGranularity(1f);

        yAxis.setAxisMinimum(40);
        yAxis.setAxisMaximum(500);

        List<Entry> earningData = new ArrayList<>();

        earningData.add(new Entry(0, 180));
        earningData.add(new Entry(1, 360));
        earningData.add(new Entry(2, 257));
        earningData.add(new Entry(3, 300));
        earningData.add(new Entry(4, 230));
        earningData.add(new Entry(5, 100));
        earningData.add(new Entry(6, 280));

        String[] weekDays = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekDays));
        xAxis.setLabelCount(earningData.size(), true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.textColor1));

        IMarker earningMarker = new EarningMarkerView(this, R.layout.marker_view);
        mEarningChart.setMarker(earningMarker);

        Legend earningLegend = mEarningChart.getLegend();
        earningLegend.setForm(Legend.LegendForm.CIRCLE);
        earningLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        earningLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        earningLegend.setTextColor(getResources().getColor(R.color.textColor1));

        LineDataSet earningDataSet = new LineDataSet(earningData, "Weekly earning");

        earningDataSet.setColor(getResources().getColor(R.color.blue));
        earningDataSet.setLineWidth(2);
        earningDataSet.setDrawCircles(false);
        earningDataSet.setDrawFilled(true);
        Drawable blueDrawable = ContextCompat.getDrawable(this, R.drawable.blue_gradient);
        earningDataSet.setFillDrawable(blueDrawable);
        earningDataSet.setDrawValues(false);
        earningDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        earningDataSet.setDrawHighlightIndicators(false);

        LineData earningLineData = new LineData();
        earningLineData.addDataSet(earningDataSet);

        mEarningChart.setData(earningLineData);
    }

    private void initMilesChart() {
        mMilesChart.setTouchEnabled(true);
        mMilesChart.setAutoScaleMinMaxEnabled(true);
        mMilesChart.getDescription().setEnabled(false);
        mMilesChart.setPinchZoom(false);
        mMilesChart.setScaleYEnabled(false);
        mMilesChart.setScaleXEnabled(false);
        mMilesChart.setExtraBottomOffset(20);

        YAxis yAxisRight = mMilesChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxis = mMilesChart.getAxisLeft();
        yAxis.setEnabled(false);
        XAxis xAxis = mMilesChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(120);

        List<Entry> milesData = new ArrayList<>();

        milesData.add(new Entry(0, 80));
        milesData.add(new Entry(1, 64));
        milesData.add(new Entry(2, 48));
        milesData.add(new Entry(3, 76));
        milesData.add(new Entry(4, 33));
        milesData.add(new Entry(5, 50));
        milesData.add(new Entry(6, 92));

        String[] weekDays = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekDays));
        xAxis.setLabelCount(milesData.size(), true);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.textColor1));

        IMarker milesMarker = new MilesMarkerView(this, R.layout.marker_view);
        mMilesChart.setMarker(milesMarker);

        Legend milesLegend = mMilesChart.getLegend();
        milesLegend.setForm(Legend.LegendForm.CIRCLE);
        milesLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        milesLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        milesLegend.setTextColor(getResources().getColor(R.color.textColor1));

        LineDataSet milesDataSet = new LineDataSet(milesData, "Miles weekly");

        milesDataSet.setColor(getResources().getColor(R.color.magenta));
        milesDataSet.setLineWidth(2);
        milesDataSet.setDrawCircles(false);
        milesDataSet.setDrawFilled(true);
        Drawable blueDrawable = ContextCompat.getDrawable(this, R.drawable.magenta_gradient);
        milesDataSet.setFillDrawable(blueDrawable);
        milesDataSet.setDrawValues(false);
        milesDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        milesDataSet.setDrawHighlightIndicators(false);

        LineData milesLineData = new LineData();
        milesLineData.addDataSet(milesDataSet);

        mMilesChart.setData(milesLineData);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
