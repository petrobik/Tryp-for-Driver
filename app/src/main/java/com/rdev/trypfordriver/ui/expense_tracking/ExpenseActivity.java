package com.rdev.trypfordriver.ui.expense_tracking;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.textfield.TextInputEditText;
import com.rdev.trypfordriver.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ExpenseActivity extends AppCompatActivity {

    private TextInputEditText oilChangeEditText;
    private TextInputEditText gasFillEditText;
    private TextInputEditText breakChangeEditText;
    private TextInputEditText tireChangeEditText;
    private TextInputEditText vehicleServiceEditText;
    private TextInputEditText carWashEditText;
    private TextInputEditText carEssentialsEditText;

    private PieChart mPieChart;


    ScrollView mScrollView;

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

        setContentView(R.layout.activity_expense);

        mPieChart = findViewById(R.id.expense_tracking_pie_chart);

        mPieChart.getDescription().setEnabled(false);
        mPieChart.setDrawEntryLabels(false);
//            mPieChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
//            mPieChart.setDrawHoleEnabled(false);
        mPieChart.setHoleRadius(60f);
        mPieChart.setDrawSlicesUnderHole(false);
        mPieChart.setRotationEnabled(false);
        mPieChart.setTouchEnabled(false);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(20f, "Oil Change"));
        entries.add(new PieEntry(35f, "Gas Fill Up"));
        entries.add(new PieEntry(35f, "Break change"));
        entries.add(new PieEntry(35f, "Tire change"));
        entries.add(new PieEntry(20f, "Vehicle service"));
        entries.add(new PieEntry(15f, "Car wash"));
        entries.add(new PieEntry(10f, "Car essentials"));

        mPieChart.setCenterText(generateCenterSpannableText());

        Legend legend = mPieChart.getLegend();
        legend.setEnabled(false);
//        legend.setWordWrapEnabled(true);
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setTextSize(12f);
//        legend.setXEntrySpace(20f);
//        legend.setYEntrySpace(5f);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//


//            legend.setTextSize(14f);

        PieDataSet dataSet = new PieDataSet(entries, "");

        final int[] COLORS = {ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.magenta),
                ContextCompat.getColor(this, R.color.pink),
                ContextCompat.getColor(this, R.color.lightblue),
                ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.lightgreen)};

        dataSet.setColors(COLORS);

        dataSet.setDrawValues(false);


//            List<String> labels = new ArrayList<>();
//            labels.add("Optimal");
//            labels.add("Normal");
//            labels.add("Elevated");
//            labels.add("Stage 1");
//            labels.add("Stage 2");
//            labels.add("Stage 3");

        PieData pieData = new PieData(dataSet);

        mPieChart.setData(pieData);

        oilChangeEditText = findViewById(R.id.oil_change_editText);
        breakChangeEditText = findViewById(R.id.break_change_editText);
        gasFillEditText = findViewById(R.id.gas_fill_editText);
        tireChangeEditText = findViewById(R.id.tire_change_editText);
        vehicleServiceEditText = findViewById(R.id.vehicle_service_editText);
        carWashEditText = findViewById(R.id.car_wash_editText);
        carEssentialsEditText = findViewById(R.id.car_essentials_editText);

//        oilChangeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    addRightDrawable(oilChangeEditText);
//                } else if (!hasFocus) {
//                    removeRightDrawable(oilChangeEditText);
//                }
//            }
//        });

//        oilChangeEditText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//
//                addRightDrawable(oilChangeEditText);
//
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    if (motionEvent.getX() >= (oilChangeEditText.getRight() - oilChangeEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        removeRightDrawable(oilChangeEditText);
//                        oilChangeEditText.clearFocus();
//                        hideKeyboard(oilChangeEditText);
//
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//
//        gasFillEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    addRightDrawable(gasFillEditText);
//                } else if (!hasFocus) {
//                    removeRightDrawable(gasFillEditText);
//                }
//            }
//        });
//
//        breakChangeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    addRightDrawable(breakChangeEditText);
//                } else if (!hasFocus) {
//                    removeRightDrawable(breakChangeEditText);
//                }
//            }
//        });
//
//        tireChangeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    addRightDrawable(tireChangeEditText);
//                } else if (!hasFocus) {
//                    removeRightDrawable(tireChangeEditText);
//                }
//            }
//        });
//
//        vehicleServiceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    addRightDrawable(vehicleServiceEditText);
//                } else if (!hasFocus) {
//                    removeRightDrawable(vehicleServiceEditText);
//                }
//            }
//        });
//
//        carWashEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    addRightDrawable(carWashEditText);
//                } else if (!hasFocus) {
//                    removeRightDrawable(carWashEditText);
//                }
//            }
//        });
//
//        carEssentialsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    addRightDrawable(carEssentialsEditText);
//                } else if (!hasFocus) {
//                    removeRightDrawable(carEssentialsEditText);
//                }
//            }
//        });


//        oilChangeEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                oilChangeEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
//                oilChangeEditText.setCompoundDrawablePadding(10 * (int) getResources().getDisplayMetrics().density);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                oilChangeEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
////                oilChangeEditText.setCompoundDrawablePadding(10 * (int) getResources().getDisplayMetrics().density);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
////                removeRightDrawable(oilChangeEditText);
//            }
//        });

    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("$10k\nTotal");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 4, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.textColor1)), 5, s.length(), 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

//    private void addRightDrawable(TextInputEditText editText) {
//        Drawable doneDrawable = ContextCompat.getDrawable(this, R.drawable.add);
//        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, doneDrawable, null);
////        editText.setCompoundDrawablePadding(5 * (int) getResources().getDisplayMetrics().density);
//    }
//
//    private void removeRightDrawable(TextInputEditText editText) {
//        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
//    }
//
//    public void hideKeyboard(View view) {
//        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }


//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
//            getWindow().getDecorView().setSystemUiVisibility(
////                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
////                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        getWindow().getDecorView().setSystemUiVisibility(
////                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
////                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//
//    }


}
