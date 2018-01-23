package com.sineva.rosapidemo.entity;

/**
 * Created by sin on 2018/1/22.
 */

public class LedColorBean {
    private String colorName;
    private int colorNumber;

    public LedColorBean(String colorName, int colorNumber) {
        this.colorName = colorName;
        this.colorNumber = colorNumber;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }
}
