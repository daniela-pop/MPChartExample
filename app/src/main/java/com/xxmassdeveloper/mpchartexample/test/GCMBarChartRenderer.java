package com.xxmassdeveloper.mpchartexample.test;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.Log;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.xxmassdeveloper.mpchartexample.R;

/**
 * Created by pop on 09-Mar-16.
 */
public class GCMBarChartRenderer extends BarChartRenderer {


    public GCMBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mShadowPaint.setColor(dataSet.getBarShadowColor());

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setBarSpace(dataSet.getBarSpace());
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));

        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);
        //float width ;
        RoundRectShape roundedRect; //= new RoundRectShape(new float[] { width, width / 3, width, width / 3, 0, 0, 0, 0 }, null, null);
        float length = 400;
       /*
             mRenderPaint.setColor(dataSet.getColor(2));
            for (int j = 0; j < buffer.size(); j += 4) {
                roundedRect = new RoundRectShape(new float[]{length, length, length, length, 0, 0, 0, 0}, null, null);
                ShapeDrawable shape = new ShapeDrawable(roundedRect);
                shape.setBounds(Math.round(buffer.buffer[j]), Math.round(buffer.buffer[j + 1]), Math.round(buffer.buffer[j + 2]),
                        Math.round(buffer.buffer[j + 3]));
                shape.getPaint().set(mRenderPaint);

                shape.draw(c);
            }
        */


        // if multiple colors
        if (dataSet.getColors().size() > 1) {

            for (int j = 0; j < buffer.size(); j += 4) {

                if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                    continue;

                if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                    break;

                if (mChart.isDrawBarShadowEnabled()) {
                    c.drawRect(buffer.buffer[j], mViewPortHandler.contentTop(),
                            buffer.buffer[j + 2],
                            mViewPortHandler.contentBottom(), mShadowPaint);
                }

                // Set the color for the currently drawn value. If the index
                // is
                // out of bounds, reuse colors.
               // mRenderPaint.setColor(dataSet.getColor(j / 4));
               // c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                   //     buffer.buffer[j + 3], mRenderPaint);


               // if (yMin > yMax) {
                  //  roundedRect = new RoundRectShape(new float[] { 0, 0, 0, 0, width, width / 3, width, width / 3 }, null, null);
               // }

                float weight = buffer.buffer[j + 2] - buffer.buffer[j];
               // Log.e("hereaa",weight+"");
            /*
                roundedRect = new RoundRectShape(new float[]{ length, length , length, length,0, 0, 0, 0}, null, null);
                ShapeDrawable shape = new ShapeDrawable(roundedRect);
                shape.setBounds(Math.round(buffer.buffer[j]), Math.round(buffer.buffer[j + 1]), Math.round(buffer.buffer[j + 2]),
                        Math.round(buffer.buffer[j + 3]));
                shape.getPaint().set(mRenderPaint);

                shape.draw(c);

            */

                GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[]{dataSet.getColor(0),dataSet.getColor(1)});
                gradient.setBounds(Math.round(buffer.buffer[j]), Math.round(buffer.buffer[j + 1]), Math.round(buffer.buffer[j + 2]),
                        Math.round(buffer.buffer[j + 3]));
                //if (mRoundedBars) {
                    gradient.setCornerRadii(new float[] { length, length, length, length, 0, 0, 0, 0 });
               // }
                gradient.draw(c);

            }
        } else {

            mRenderPaint.setColor(dataSet.getColor());

            for (int j = 0; j < buffer.size(); j += 4) {

                if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                    continue;

                if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                    break;
                /*
                if (mChart.isDrawBarShadowEnabled()) {
                    c.drawRect(buffer.buffer[j], mViewPortHandler.contentTop(),
                            buffer.buffer[j + 2],
                            mViewPortHandler.contentBottom(), mShadowPaint);
                } */

                /*
                roundedRect = new RoundRectShape(new float[]{ length, length , length, length,0, 0, 0, 0}, null, null);
                ShapeDrawable shape = new ShapeDrawable(roundedRect);
                shape.setBounds(Math.round(buffer.buffer[j]), Math.round(buffer.buffer[j + 1]), Math.round(buffer.buffer[j + 2]),
                        Math.round(buffer.buffer[j + 3]));
                shape.getPaint().set(mRenderPaint);
                shape.draw(c);
                */

                GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,new int[]{0x9c00ff,0x3ff1ff});
                gradient.setBounds(Math.round(buffer.buffer[j]), Math.round(buffer.buffer[j + 1]), Math.round(buffer.buffer[j + 2]),
                        Math.round(buffer.buffer[j + 3]));
                //if (mRoundedBars) {
                gradient.setCornerRadii(new float[] { length, length, length, length, 0, 0, 0, 0 });
                gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                // }
                gradient.draw(c);
            }
        }

    }

}
