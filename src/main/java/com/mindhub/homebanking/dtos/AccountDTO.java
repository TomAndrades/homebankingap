package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDateTime date;
    private Double balance;
    private Set<TransactionDTO> transaction;

    public AccountDTO(Account account) {
        id = account.getId();
        number = account.getNumber();
        date = account.getCreationDate();
        balance = account.getBalance();
        transaction = account.getTransactions().stream().map(TransactionDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return date;
    }

    public Double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransaction() {
        return transaction;
    }
}
