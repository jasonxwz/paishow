package com.example.asus.paishow.ext;

/**
 * Created by Charmer on 2016/10/6.
 */
public class ArrayDegreeProvider implements IDegreeProvider {
    private float[] degrees;

    public ArrayDegreeProvider(float[] degrees) {
        this.degrees = degrees;
    }

    public float[] getDegrees(int count, float totalDegrees){
        if(degrees == null || degrees.length != count){
            throw new IllegalArgumentException("Provided delta degrees and the action count are not the same.");
        }
        return degrees;
    }
}
