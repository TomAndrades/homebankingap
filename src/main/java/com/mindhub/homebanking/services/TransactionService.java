package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactionsDTO();
    List<Transaction> getTransactions();
}
