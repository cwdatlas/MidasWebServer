package com.midaswebserver.midasweb.apiModels;

public class BacktradeOptimize extends BacktradeData {
    public double startSma;
    public double endSma;
    public double startEma;
    public double endEma;

    public double getStartSma() {
        return startSma;
    }

    public void setStartSma(double startSma) {
        this.startSma = startSma;
    }

    public double getEndSma() {
        return endSma;
    }

    public void setEndSma(double endSma) {
        this.endSma = endSma;
    }

    public double getStartEma() {
        return startEma;
    }

    public void setStartEma(double startEma) {
        this.startEma = startEma;
    }

    public double getEndEma() {
        return endEma;
    }

    public void setEndEma(double endEma) {
        this.endEma = endEma;
    }

    @Override
    public String toString() {
        return "BacktradeOptimize{" +
                "startSma=" + startSma +
                ", endSma=" + endSma +
                ", startEma=" + startEma +
                ", endEma=" + endEma +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", stockTicker=" + stockTicker +
                ", algorithm=" + algorithm +
                ", commission=" + commission +
                ", backtradeType='" + backtradeType + '\'' +
                '}';
    }
}
