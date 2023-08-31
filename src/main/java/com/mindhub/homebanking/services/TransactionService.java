package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TransactionService {
    void saveTransaction(Transaction transaction);

    List<TransactionDTO> getTransactionsDTO();
    List<Transaction> getTransactions();

    void createTransaction(Account originAccount, Account destinyAccount, Double amount, String description);
}
