package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createCurrentAccount(Authentication authentication){
        if (authentication != null){
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size() == 3){
            return new ResponseEntity<>("You already have 3 accounts",HttpStatus.FORBIDDEN);
        }
            Account account = new Account( generateAccountNumber(), LocalDateTime.now(), 0.0);
            client.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("You must need to be logged before create an account",HttpStatus.FORBIDDEN);
        }
    }


    public ResponseEntity<Object> createAccount(Client client){
        if (client.getAccounts().size() == 3){
            return new ResponseEntity<>("You already have 3 accounts",HttpStatus.FORBIDDEN);
        }
            Account account = new Account( generateAccountNumber(), LocalDateTime.now(), 0.0);

            client.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }


    public String generateAccountNumber() {
        int min = 0;
        int max = 9999999;
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = "VIN-" + random.nextInt(max - min) + min;
        } while (null != accountRepository.findByNumber(accountNumber));
        return accountNumber;

    }
}
