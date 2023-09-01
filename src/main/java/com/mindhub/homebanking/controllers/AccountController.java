package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.implement.AccountServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){ return accountService.getAccountsDTO(); }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){ return accountService.getAccountDTO(id); }

    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication){
        return accountService.getCurrentAccountsDTO(authentication.getName());
    }

    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createCurrentAccount(Authentication authentication){
        if (clientService.clientExist(authentication.getName())){
            Client client = clientService.getClientByEmail(authentication.getName());
            if (client.getAccounts().size() >= 3){
                return new ResponseEntity<>("You already have 3 accounts",HttpStatus.FORBIDDEN);
            }
            accountService.createAccount(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("You must need to be logged before create an account", HttpStatus.FORBIDDEN);
        }
    }

}
