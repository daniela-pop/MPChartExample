package com.xxmassdeveloper.mpchartexample.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import com.xxmassdeveloper.mpchartexample.R;
import com.xxmassdeveloper.mpchartexample.custom.MyYAxisValueFormatter;


/**
 * Created by pop on 08-Mar-16.
 */
public class BarChartConfigurator {

    @Nullable
    protected BarChart mChart;
    private Context context;


    public BarChartConfigurator(Context context) {
        this.context = context;
        // mLabelColor = ContextCompat.getColor(context, R.color.palette_gray_3);
        //mNoDataLabel = mContext.getString(R.string.txt_empty_page_no_data);
    }



    @TargetApi(Build.VERSION_CODES.M)
    public void setupChart(BarChart chart) {

        mChart = chart;

        mChart.setDescription("");
        mChart.getLegend().setEnabled(false);

        // disable touch gestures
        mChart.setTouchEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setDescription("");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        //mChart.setDrawYLabels(false);
        mChart.getAxisRight().setEnabled(false);
       mChart.setRenderer(new GCMBarChartRenderer(mChart, mChart.getAnimator(), mChart.getViewPortHandler()));

        // Get the paint renderer to create the line shading.
        Paint paint = mChart.getRenderer().getPaintRender();


        LinearGradient linGrad = new LinearGradient(0, 0, 0, 700,
                context.getColor(R.color.gcm3_chart_gradient_purple_blue_end),
                context.getColor(R.color.gcm3_chart_gradient_purple_blue_start),
                Shader.TileMode.MIRROR);
        //paint.setShader(linGrad);
        /*
        List<IBarDataSet> sets = mChart.getData().getDataSets();

        for (IBarDataSet iSet : sets) {

            BarDataSet set = (BarDataSet) iSet;
            //set.setDrawFilled(false);
            //set.setColor();
            //set.setColors();
        }
    }
        */

        //setupLineChartData();

        Typeface mTf = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
       // xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);
        xAxis.setEnabled(false);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        // Don't forget to refresh the drawing
        mChart.invalidate();
    }
}
