package com.bubble.crowdsourcingcryptocurrencyprediction.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * Created by webert3 on 2/10/18.
 */

public class PriceMarkerView extends MarkerView {
    private TextView priceMarker;
    private int screenWidth;

    public PriceMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);
        priceMarker = (TextView) findViewById(R.id.price_marker_tv);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e.getData() == null) {
            priceMarker.setText(String.format("Price: $%.2f", e.getY()));
        }
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }

    @Override
    public void draw(Canvas canvas, float posx, float posy)
    {
        // Check marker position and update offsets.
        int w = getWidth();
        if((screenWidth-posx-w) < w) {
            posx -= w;
        }
        posy -= 65;
        // translate to the correct position and draw
        canvas.translate(posx, posy);
        draw(canvas);
        canvas.translate(-posx, -posy);
    }
}
