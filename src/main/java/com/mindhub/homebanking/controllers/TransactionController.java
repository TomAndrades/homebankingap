package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionService.getTransactionsDTO();
    }

    @Transactional
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(@RequestParam(defaultValue = "0") Double amount, @RequestParam String description,
                                                    @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
                                                    Authentication authentication){
        if (authentication == null){
            return new ResponseEntity<>("You need to be logged before of create a transaction",HttpStatus.FORBIDDEN);
        }
        else if(amount <= 0.0) {
            return new ResponseEntity<>("You must set an positive amount",HttpStatus.FORBIDDEN);
        }
        else if (description.isBlank()){
            return new ResponseEntity<>("You must set description",HttpStatus.FORBIDDEN);
        }
        else if( fromAccountNumber.isBlank()){
            return new ResponseEntity<>("You must set an origin account",HttpStatus.FORBIDDEN);
        }
        else if(toAccountNumber.isBlank()){
            return new ResponseEntity<>("You must set an destiny account",HttpStatus.FORBIDDEN);
        }
        else if(!accountService.existsAccountByNumber(fromAccountNumber)){
            return new ResponseEntity<>("The origin account doesn't exists",HttpStatus.FORBIDDEN);
        }
        else if(!accountService.existsAccountByNumber(toAccountNumber)){
            return new ResponseEntity<>("The destiny account doesn't exists",HttpStatus.FORBIDDEN);
        }
        else
        {
            Account originAccount = accountService.getAccountByNumber(fromAccountNumber);
            Account destinyAccount = accountService.getAccountByNumber(toAccountNumber);

            if(originAccount.getClient() != clientService.getClientByEmail(authentication.getName())){
                return new ResponseEntity<>("You are not the owner of the origin account selected",HttpStatus.FORBIDDEN);
            }
            else if(originAccount.getBalance() < amount){
                return new ResponseEntity<>("The origin account selected hasn't funds enough",HttpStatus.FORBIDDEN);
            }
            else if(originAccount == destinyAccount){
                return new ResponseEntity<>("The origin and destiny account selected are the same",HttpStatus.FORBIDDEN);
            } else {
                transactionService.createTransaction(originAccount, destinyAccount, amount, description);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }

    }

}
