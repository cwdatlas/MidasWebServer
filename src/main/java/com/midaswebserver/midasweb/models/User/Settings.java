package com.midaswebserver.midasweb.models.User;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_settings")
public class Settings {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User userId;
    @Column(name = "searched_tickers", nullable = true)
    private List<String> searchedTickers = new ArrayList<String>();

}
