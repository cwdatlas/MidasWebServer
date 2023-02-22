package com.midaswebserver.midasweb.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ticker")
public class Ticker {
    @Id
    @GeneratedValue
    private Long ID;
    @Column(name = "ticker", nullable = false, unique = true)
    private String ticker;

    public Ticker(){
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticker ticker1)) return false;
        return Objects.equals(ID, ticker1.ID) && Objects.equals(getTicker(), ticker1.getTicker());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, getTicker());
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "ID=" + ID +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
