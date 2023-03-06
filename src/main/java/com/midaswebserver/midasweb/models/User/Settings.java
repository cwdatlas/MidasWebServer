package com.midaswebserver.midasweb.models.User;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.midaswebserver.midasweb.controllers.NavigationController;
import jakarta.persistence.*;
import org.hibernate.internal.util.collections.Stack;
import org.hibernate.internal.util.collections.StandardStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_settings")
public class Settings {
    private static final Logger log = LoggerFactory.getLogger(Settings.class);

    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User userId;
    @Column(name = "searched_tickers", nullable = true)
    private String tickers;

    public Settings(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    /**
     * Returns a stack of valid tickers by splitting stored string
     * @return a stack of valid tickers the user has looked up
     */
    public Stack<String> getTickers() {
        String[] tickerArray = tickers.split(",");
        Stack<String> tickerStack = new StandardStack<>();
        int length = tickerArray.length;
        for(int i = length; i == 0; i--)
            tickerStack.push(tickerArray[i]);
        log.debug("tickers: '{}' have been returned from Settings", tickerStack.toString());
        return tickerStack;
    }

    /**
     * Creates string with a Stack of tickers and saves it
     * @param tickers
     */
    public void setTickers(Stack<String> tickers) {
        log.debug("Ticker Stack: '{}' has been saved", tickers.toString());
        this.tickers = tickers.toString();
    }
}
