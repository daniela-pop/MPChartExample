package com.xxmassdeveloper.mpchartexample.test;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.CircleBuffer;
import com.github.mikephil.charting.buffer.LineBuffer;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Custom {@link LineChartRenderer} used to display stepped line charts.
 * To represent a ILineDataSet as a stepped chart set the  {@code drawCubicEnabled} flag to {@code true}.
 * Otherwise renderer behaves like the LineChartRenderer.
 * <p/>
 * Can be used to display Line and Stepped Line datasets at the same time.
 * <p/>
 * Created by catana on 1/21/2016.
 */
public class SteppedLineChartRenderer extends LineChartRenderer {

    public SteppedLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void initBuffers() {
        LineData lineData = mChart.getLineData();
        mLineBuffers = new LineBuffer[lineData.getDataSetCount()];
        mCircleBuffers = new CircleBuffer[lineData.getDataSetCount()];

        for (int i = 0; i < mLineBuffers.length; i++) {
            ILineDataSet set = lineData.getDataSetByIndex(i);
            mLineBuffers[i] = getLineBuffer(set);
            mCircleBuffers[i] = new CircleBuffer(set.getEntryCount() * 2);
        }
    }

    private LineBuffer getLineBuffer(ILineDataSet dataset) {
        if (dataset.isDrawCubicEnabled()) {
            // isDrawCubicEnabled set on the dataset, use SteppedLineBuffer
            // for draw points construction.
            //return new SteppedLineBuffer(dataset.getEntryCount() * 8);
        }
        return new LineBuffer(dataset.getEntryCount() * 4 - 4);
    }

    @Override
    protected void drawCubic(Canvas c, ILineDataSet dataSet) {
        //
        // Implementation taken from parent drawLinear(Canvas c, ILineDataSet dataSet)
        // cannot use super as parent uses:
        //
        //        canvas.drawLines(buffer.buffer, 0, range, mRenderPaint);
        //
        // Instead of
        //        c.drawLines(buffer.buffer, mRenderPaint);
        //
        // range is not the size of the whole buffer length but it is 'hardcoded' as (maxx - minx) * 4 - 4;
        //
        // Given the custom SteppedLineBuffer and it's extended length only half the lines are
        // drawn and as a result the duplication (minus infringing line) of the parent's drawLines method
        //

        int entryCount = dataSet.getEntryCount();

        int dataSetIndex = mChart.getLineData().getIndexOfDataSet(dataSet);

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        mRenderPaint.setStyle(Paint.Style.STROKE);

        Canvas canvas = null;

        // if the data-set is dashed, draw on bitmap-canvas
        if (dataSet.isDashedLineEnabled()) {
            canvas = mBitmapCanvas;
        } else {
            canvas = c;
        }

        Entry entryFrom = dataSet.getEntryForXIndex((mMinX < 0) ? 0 : mMinX, DataSet.Rounding.DOWN);
        Entry entryTo = dataSet.getEntryForXIndex(mMaxX, DataSet.Rounding.UP);

        int minX = Math.max(dataSet.getEntryIndex(entryFrom), 0);
        int maxX = Math.min(dataSet.getEntryIndex(entryTo) + 1, entryCount);

        int range = (maxX - minX) * 4 - 4;

        LineBuffer buffer = mLineBuffers[dataSetIndex];
        buffer.setPhases(phaseX, phaseY);
        buffer.limitFrom(minX);
        buffer.limitTo(maxX);
        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        // more than 1 color
        if (dataSet.getColors().size() > 1) {
            for (int j = 0; j < range; j += 4) {
                if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j])) break;

                // make sure the lines don't do shitty things outside bounds
                if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]) ||
                                (!mViewPortHandler.isInBoundsTop(buffer.buffer[j + 1]) && !mViewPortHandler.isInBoundsBottom(buffer.buffer[j + 3])) ||
                                (!mViewPortHandler.isInBoundsTop(buffer.buffer[j + 1]) && !mViewPortHandler.isInBoundsBottom(buffer.buffer[j + 3])))
                    continue;

                // get the color that is set for this line-segment
                mRenderPaint.setColor(dataSet.getColor(j / 4 + minX));

                final float startX = buffer.buffer[j];
                final float startY = buffer.buffer[j + 1];
                final float stopX = buffer.buffer[j + 2];
                final float stopY = buffer.buffer[j + 3];

                canvas.drawLine(startX, startY, stopX, stopY, mRenderPaint);
            }

        } else {
            // only one color per dataset
            mRenderPaint.setColor(dataSet.getColor());
            canvas.drawLines(buffer.buffer, mRenderPaint);
        }

        mRenderPaint.setPathEffect(null);

        // if drawing filled is enabled
        if (dataSet.isDrawFilledEnabled() && entryCount > 0) {
            drawLinearFill(c, dataSet, minX, maxX, trans);
        }
    }
}
