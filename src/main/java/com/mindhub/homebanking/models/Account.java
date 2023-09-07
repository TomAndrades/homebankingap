package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<>();

    public Account(String number, LocalDateTime creationDate, Double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Account(){};

    public Client getClient(){
        return client;
    }
    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public Set<Transaction> getTransactions(){
        return transactions;
    }

    public Double getBalance() {
        return balance;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
        transaction.setAccount(this);
    }
    public void addBalance(Double amount){
        this.balance += amount;
    }
    public void subtractBalance(Double amount){
        this.balance -= amount;
    }
}
