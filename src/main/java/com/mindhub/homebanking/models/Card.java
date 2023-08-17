package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    private CardType type;

    private CardColor color;
    private String cardHolder;
    private String number;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;
    private Short cvv;

    public Card(CardType type, CardColor color, String number, LocalDateTime fromDate, Short cvv) {
        this.type = type;
        this.color = color;
        this.number = number;
        this.fromDate = fromDate;
        this.thruDate = fromDate.plusYears(5);
        this.cvv = cvv;
    }

    public Card(){}

    public Long getId() {
        return id;
    }
@JsonIgnore
    public Client getClient() {
        return client;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public Short getCvv() {
        return cvv;
    }

    public void setClient(Client client) {
        this.client = client;
        this.cardHolder = (client.getFirstName() + " " + client.getLastName());
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public void setCvv(Short cvv) {
        this.cvv = cvv;
    }
}
