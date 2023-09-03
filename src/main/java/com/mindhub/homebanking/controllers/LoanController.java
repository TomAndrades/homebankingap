package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    LoanService loanService;
    @Autowired
    ClientLoanService clientLoanService;
    @Autowired
    TransactionService transactionService;

//    @RequestMapping("/loans")
//    public List<LoanDTO> getLoans(){
//        return loanService.getAllLoansDTO();
//    }
    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getLoansDTO();
    }
    @Transactional
    @RequestMapping(value = "/loans",method = RequestMethod.POST)
    public ResponseEntity<Object> createLoanApplication(@RequestBody LoanApplicationDTO loanApplication, Authentication authentication){
        if (authentication == null){
            return new ResponseEntity<>("You must need to be logged before create an loan application", HttpStatus.FORBIDDEN);
        }
        else if (clientService.clientExist(authentication.getName())){
            Client client = clientService.getClientByEmail(authentication.getName());
            if ( !loanService.existsLoanById(loanApplication.getLoanId())){
                return new ResponseEntity<>("The selected loan not exists, try apply to a valid loan", HttpStatus.FORBIDDEN);
            } else {
                Loan loan = loanService.getLoanById(loanApplication.getLoanId());

                if (loanApplication.getAmount() <= 0){
                    return new ResponseEntity<>("You must need to enter an possitive amount", HttpStatus.FORBIDDEN);
                } else if (loanApplication.getAmount() > loan.getMaxAmount()){
                    return  new ResponseEntity<>("Your amount exceeds the max amount that you can get",HttpStatus.FORBIDDEN);
                } else if (!loan.getPayments().contains(loanApplication.getPayments())){
                    return  new ResponseEntity<>("The number of payments selected is not contained in this loan payment options",HttpStatus.FORBIDDEN);
                } else if (!accountService.existsAccountByNumber(loanApplication.getAccountNumber())) {
                    return new ResponseEntity<>("The account selected not exists", HttpStatus.FORBIDDEN);
                } else if (accountService.getAccountByNumber(loanApplication.getAccountNumber()).getClient() != client){
                    return new ResponseEntity<>("You aren't the owner of the account selected", HttpStatus.FORBIDDEN);
                } else {
                    loanService.createLoan(loanApplication, client);
                }
            }


            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("You must need to be logged before create an loan application", HttpStatus.FORBIDDEN);
        }
    }
}
