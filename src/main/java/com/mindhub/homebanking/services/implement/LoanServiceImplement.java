package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

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
    public List<LoanDTO> getAllLoansDTO(){
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

}