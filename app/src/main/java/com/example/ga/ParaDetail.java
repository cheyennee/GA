package com.example.ga;

public class ParaDetail {
    String name;
    int lbound;
    int ubound;
    int ratio;
    int power;

    public ParaDetail(){}

    public ParaDetail(String name, int lbound, int ubound, int ratio,int power) {
        this.name = name;
        this.lbound = lbound;
        this.ubound = ubound;
        this.ratio = ratio;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLbound() {
        return lbound;
    }

    public void setLbound(int lbound) {
        this.lbound = lbound;
    }

    public int getUbound() {
        return ubound;
    }

    public void setUbound(int ubound) {
        this.ubound = ubound;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
