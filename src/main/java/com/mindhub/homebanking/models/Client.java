package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Client(){}
    public Client(String first, String last, String email) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts(){
        return accounts;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addAccount(Account account){
        this.accounts.add(account);
        account.setClient(this);
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    public Set<Loan> getLoans(){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(toSet());
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
