package com.midaswebserver.midasweb.models.User;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

@Entity
@Table(name = "user_settings")
public class Settings {
    private static final Logger log = LoggerFactory.getLogger(Settings.class);

    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
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
        return user;
    }

    public void setUserId(User userId) {
        this.user = userId;
    }

    /**
     * Returns a stack of valid tickers by splitting stored string
     * @return a stack of valid tickers the user has looked up
     */
    public Stack<String> getTickers() {
        String[] tickerArray = tickers.split(",");
        Stack<String> tickerStack = new Stack<>();
        int length = tickerArray.length;
        log.debug("Length of ticker array: '{}'", length);
        for(int i = 0; i < length; i++) {
            tickerStack.push(tickerArray[i]);
        }
        log.debug("tickers: '{}' have been returned from Settings", tickerStack.toString());
        return tickerStack;
    }

    /**
     * Creates string with a Stack of tickers and saves it
     * @param tickers
     */
    public void setTickers(Stack<String> tickers) {
        log.debug("Ticker Stack: '{}' has been saved", tickers.toString());
        String localTickers = tickers.toString();
        localTickers = localTickers.replace("[", "");
        localTickers = localTickers.replace("]", "");
        localTickers = localTickers.replace(" ", "");
        this.tickers = localTickers;
    }
}
