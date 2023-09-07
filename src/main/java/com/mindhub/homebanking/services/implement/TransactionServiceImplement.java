package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
    @Override
    public List<TransactionDTO> getTransactionsDTO(){
        return getTransactions().stream().map(TransactionDTO::new).collect(toList());
    }
    @Override
    public List<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    @Override
    public void createTransaction(Account originAccount, Account destinyAccount, Double amount, String description) {
        Transaction transactionDEBIT = new Transaction(TransactionType.DEBIT, -amount,description + " " + destinyAccount.getNumber());
        Transaction transactionCREDIT = new Transaction(TransactionType.CREDIT, amount,description + " " + originAccount.getNumber());
        originAccount.addTransaction(transactionDEBIT);
        originAccount.subtractBalance(amount);
        destinyAccount.addTransaction(transactionCREDIT);
        destinyAccount.addBalance(amount);
        saveTransaction(transactionDEBIT);
        saveTransaction(transactionCREDIT);
        accountRepository.save(originAccount);
        accountRepository.save(destinyAccount);
    }

}
