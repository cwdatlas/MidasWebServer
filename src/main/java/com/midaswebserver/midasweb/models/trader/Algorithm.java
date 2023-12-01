package com.midaswebserver.midasweb.models.trader;

public enum Algorithm {
    SMAEMACross,
    SMAEMAVolumeCross,
    SMAEMAVolumeCrossPlusShort;

    @Override
    public String toString() {
        return this.name();
    }
}
