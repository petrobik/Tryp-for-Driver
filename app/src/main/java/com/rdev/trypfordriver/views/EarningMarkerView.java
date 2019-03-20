package com.rdev.trypfordriver.views;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.rdev.trypfordriver.R;

public class EarningMarkerView extends MarkerView {

    private TextView mMarkerValueTextView;

    public EarningMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        mMarkerValueTextView = findViewById(R.id.marker_value_textView);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        mMarkerValueTextView.setText("$" + (int) e.getY());

        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if (mOffset == null) {
            mOffset = new MPPointF(-(getWidth() / 2), (-getHeight() + 35));
        }

        return mOffset;
    }
}
