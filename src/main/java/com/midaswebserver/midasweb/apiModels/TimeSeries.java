package com.midaswebserver.midasweb.apiModels;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midaswebserver.midasweb.apiModels.TimeEntry;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TimeSeries {
    @JsonIgnoreProperties(ignoreUnknown = true)
    TimeSeries(){

    }
    @NotNull
    @JsonAnyGetter
    private List<TimeEntry> timeSeries = new ArrayList<>();;

    public List<TimeEntry> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeEntry> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
