package com.xxmassdeveloper.mpchartexample.test;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;



import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class HorizontalBarChartConfigurator {

    private static final String TAG = "HorizontalBarChartConf";

    protected final Context mContext;
    /*
    @ColorInt
    private final int mLabelColor;
    private final String mNoDataLabel;
    */

    @Nullable
    protected HorizontalBarChart mChart;
    private XAxis mXAxis;

    public HorizontalBarChartConfigurator(Context context) {
        this.mContext = context;
        // mLabelColor = ContextCompat.getColor(context, R.color.palette_gray_3);
        //mNoDataLabel = mContext.getString(R.string.txt_empty_page_no_data);
    }


    /**
     * Sets up the default chart options.
     */
    public void setupChart(HorizontalBarChart chartView) {
        mChart = chartView;
        if (mChart == null) {
            Log.e(TAG, "Chart misconfiguration - unknown id");
            return;
        }

        mChart.setDescription("");
        mChart.getLegend().setEnabled(false);

        // disable touch gestures
        mChart.setTouchEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        mXAxis = mChart.getXAxis();
        mXAxis.setTextColor(Color.WHITE);
        mXAxis.setPosition(XAxis.XAxisPosition.TOP);
        mXAxis.setDrawGridLines(false);
        mXAxis.setDrawAxisLine(false);
        mXAxis.setAxisLineColor(getLabelColor());
        mXAxis.setTextSize(10f);


     //   mChart.setExtraBottomOffset(XDottedAxisRenderer.BOTTOM_OFFSET);
    }

    /**
     * Sets the chart data.
     *
     * @param data {@link LineData}
     */
    protected void setChartData(@Nullable BarData data) {
        if (mChart != null) {
            mChart.setData(data);
        }
    }

    /*
        protected void setYAxisLabels(@MPChart.DottedPattern int dotPattern, @MPChart.LabelPattern int labelPattern) {
            if (mChart != null) {
                final ViewPortHandler viewPortHandler = mChart.getViewPortHandler();
                final Transformer transformer = mChart.getTransformer(YAxis.AxisDependency.LEFT);
                final YDottedAxisRendererHorizontalBarChart yAxisRenderer = new YDottedAxisRendererHorizontalBarChart(viewPortHandler,
                                mChart.getAxisRight(), transformer, dotPattern, labelPattern);
                mChart.setRendererRightYAxis(yAxisRenderer);
            }
        }
    */
    @ColorInt
    protected int getLabelColor() {
        return -1;// mLabelColor;
    }

    /**
     * Invalidates the chart and sets the empty chart message.
     *
     * @see LineChartConfigurator#getEmptyChartMessage()
     */
    protected void displayEmptyChart() {
        if (mChart != null) {
            mChart.setNoDataText(getEmptyChartMessage());
        }
        invalidateChart();
    }

    /**
     * Returns the string message to be displayed if the chart has no data.
     * <p/>
     * Override this method to customize displayed string.
     */
    protected String getEmptyChartMessage() {
        return "";//mNoDataLabel;
    }

    /**
     * Invalidate the whole view. Must be called from the UI thread.
     */
    protected void invalidateChart() {
        if (mChart != null) {
            mChart.invalidate();
        }
    }
}
