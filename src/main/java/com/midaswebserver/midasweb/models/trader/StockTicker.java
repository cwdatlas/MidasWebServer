package com.midaswebserver.midasweb.models.trader;

public enum StockTicker {
    CRM,
    REGN,
    SCHW,
    CMG,
    EOG;

    @Override
    public String toString() {
        return this.name();
    }
}
