package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    ClientService clientService;

    @Transactional
    @RequestMapping(value = "/loans",method = RequestMethod.POST)
    public ResponseEntity<Object> createLoanApplication(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        if (clientService.clientExist(authentication.getName())){
            Client client = clientService.getClientByEmail(authentication.getName());


            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("You must need to be logged before create an loan application", HttpStatus.FORBIDDEN);
        }
    }
}
