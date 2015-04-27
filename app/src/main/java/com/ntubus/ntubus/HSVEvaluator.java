package com.ntubus.ntubus;

import android.animation.TypeEvaluator;
import android.graphics.Color;

public class HSVEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        float startHSV[] = new float[3];
        Color.RGBToHSV(startA, startG, startB, startHSV);

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        float endHSV[] = new float[3];
        Color.RGBToHSV(endR, endG, endB, endHSV);

        return Color.HSVToColor((int) (startA + fraction * (endA - startA)),
                new float[]{
                        fraction * (endHSV[0] - startHSV[0]) + startHSV[0],
                        fraction * (endHSV[1] - startHSV[1]) + startHSV[1],
                        fraction * (endHSV[2] - startHSV[2]) + startHSV[2]
                });


    }
}