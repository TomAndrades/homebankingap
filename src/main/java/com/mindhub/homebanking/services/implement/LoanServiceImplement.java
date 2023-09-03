package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public boolean existsLoanById(Long id) {
        return loanRepository.existsById(id);
    }

    @Override
    public Loan getLoanById(Long id) { return loanRepository.findLoanById(id).orElse(null); }

    @Override
    public LoanDTO getLoanDTOById(Long id) { return new LoanDTO(getLoanById(id)); }

    @Override
    public List<LoanDTO> getLoansDTO(){
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public void createLoan(LoanApplicationDTO loanApplication, Client client) {
        ClientLoan clientLoan = new ClientLoan(loanApplication.getAmount(),loanApplication.getPayments());
        clientLoan.setLoan(getLoanById(loanApplication.getLoanId()));
        clientLoan.setClient(client);
        Transaction transaction = new Transaction(TransactionType.CREDIT,loanApplication.getAmount(),
                getLoanById(loanApplication.getLoanId()).getName() + " Loan Aprroved");
        Account destinyAccount = accountRepository.findByNumber(loanApplication.getAccountNumber());
        destinyAccount.addTransaction(transaction);
        clientLoanRepository.save(clientLoan);
        transactionRepository.save(transaction);
    }

}