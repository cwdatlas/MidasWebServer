package com.midaswebserver.midasweb.models.trader;

public enum Algorithm {
    EmaSmaCross,
    EmaEmaCross,
    SmaSmaCross;

    @Override
    public String toString() {
        return this.name();
    }
}