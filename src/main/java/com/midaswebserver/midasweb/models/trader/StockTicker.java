package com.midaswebserver.midasweb.models.trader;

public enum StockTicker {
    CRM, //Technology: (Salesforce.com Inc.)
    REGN, //Healthcare: (Regeneron Pharmaceuticals Inc.)
    SCHW, //Finance: (Charles Schwab Corporation)
    CMG, //Consumer Discretionary: (Chipotle Mexican Grill)
    EOG; //Energy: (EOG Resources Inc.)

    @Override
    public String toString() {
        return this.name();
    }
}
