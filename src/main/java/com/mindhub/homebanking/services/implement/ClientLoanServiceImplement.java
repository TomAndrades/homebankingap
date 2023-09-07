package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {

    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public void saveClientLoan(ClientLoan clientLoan){
        clientLoanRepository.save(clientLoan);
    }
    @Override
    public List<ClientLoan> getClientLoans(){
        return clientLoanRepository.findAll();
    }
    @Override
    public List<ClientLoanDTO> getClientLoansDTO(){
        return toDTO(getClientLoans());
    }
    @Override
    public List<ClientLoan> getClientLoansByClient(Client client){
        return clientLoanRepository.findClientLoansByClient(client);
    }
    @Override
    public List<ClientLoanDTO> getClientLoansDTOByClient(Client client){
        return toDTO(clientLoanRepository.findClientLoansByClient(client));
    }

    @Override
    public List<ClientLoanDTO> toDTO(List<ClientLoan> list){
        return list.stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList());
    }


    @Override
    public void createClientLoan(LoanApplicationDTO loanApplication, Loan loan,Client client) {
        Double clientLoanAmount = loanApplication.getAmount()*.2;
        ClientLoan clientLoan = new ClientLoan(clientLoanAmount, loanApplication.getPayments());
        Transaction transaction = new Transaction(TransactionType.CREDIT,loanApplication.getAmount(),
                loan.getName() + " Loan Aprroved");
        Account destinyAccount = accountRepository.findByNumber(loanApplication.getAccountNumber());

        clientLoan.setLoan(loan);
        clientLoan.setClient(client);

        destinyAccount.addTransaction(transaction);
        destinyAccount.addBalance(loanApplication.getAmount());

        saveClientLoan(clientLoan);
        transactionRepository.save(transaction);
        accountRepository.save(destinyAccount);
    }

}
