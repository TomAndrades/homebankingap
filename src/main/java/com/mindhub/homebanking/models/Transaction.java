package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;


    public Transaction(TransactionType type, Double amount, String description)
    {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
    }
    public Transaction() {}

    public Long getId() {
        return id;
    }

    public Account getAccount(){
        return account;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setDate(LocalDateTime date) {
    this.date = date;
    }

    public void setAccount(Account account){
        this.account = account;
    }

}

