package com.midaswebserver.midasweb.models.User;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * @Author Aidan Scott
 * @since 0.0.1
 * @version 0.0.1
 * the {@value Setting} is used to persistently store a user search of a symbol
 */
@Entity
@Table(name = "user_settings")
public class Setting {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
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
    public String getTicker() {
        return this.ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
