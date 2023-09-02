package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
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

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getAllLoansDTO();
    }
    @RequestMapping("/clients/current/loans")
    public List<ClientLoanDTO> getCurrentLoans(Authentication authentication){
        return clientLoanService.getClientLoansDTOByClient(clientService.getClientByEmail(authentication.getName()));
    }
    @Transactional
    @RequestMapping(value = "/loans",method = RequestMethod.POST)
    public ResponseEntity<Object> createLoanApplication(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        if (authentication == null){
            return new ResponseEntity<>("You must need to be logged before create an loan application", HttpStatus.FORBIDDEN);
        }
        else if (clientService.clientExist(authentication.getName())){
            Client client = clientService.getClientByEmail(authentication.getName());
            if ( !loanService.existsLoanById(loanApplicationDTO.getLoanId())){
                return new ResponseEntity<>("The selected loan not exists, try apply to a valid loan", HttpStatus.FORBIDDEN);
            } else {
                Loan loan = loanService.getLoanById(loanApplicationDTO.getLoanId());

                if (loanApplicationDTO.getAmount() <= 0){
                    return new ResponseEntity<>("You must need to enter an possitive amount", HttpStatus.FORBIDDEN);
                } else if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
                    return  new ResponseEntity<>("Your amount exceeds the max amount that you can get",HttpStatus.FORBIDDEN);
                } else if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
                    return  new ResponseEntity<>("The number of payments selected is not contained in this loan payment options",HttpStatus.FORBIDDEN);
                } else if (!accountService.existsAccountByNumber(loanApplicationDTO.getAccountNumber())) {
                    return new ResponseEntity<>("The account selected not exists", HttpStatus.FORBIDDEN);
                } else if (accountService.getAccountByNumber(loanApplicationDTO.getAccountNumber()).getClient() != client){
                    return new ResponseEntity<>("You aren't the owner of the account selected", HttpStatus.FORBIDDEN);
                } else {
                    ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount(),loanApplicationDTO.getPayments());
                    clientLoan.setLoan(loan);
                    clientLoan.setClient(client);
                    clientLoanService.saveClientLoan(clientLoan);
                }
            }


            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("You must need to be logged before create an loan application", HttpStatus.FORBIDDEN);
        }
    }
}
