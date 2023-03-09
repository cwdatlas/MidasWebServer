package com.midaswebserver.midasweb.models.User;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

@Entity
@Table(name = "user_settings")
public class Setting {
    private static final Logger log = LoggerFactory.getLogger(Setting.class);

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "searched_ticker", nullable = true)
    private String ticker;

    public Setting(){
    }

    public Setting(String ticker){
        this.ticker = ticker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User userId) {
        this.user = userId;
    }

    /**
     * Returns a stack of valid tickers by splitting stored string
     * @return a stack of valid tickers the user has looked up
     */
    public String getTicker() {
        return this.ticker;
    }

    /**
     * Creates string with a Stack of tickers and saves it
     * @param ticker
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
