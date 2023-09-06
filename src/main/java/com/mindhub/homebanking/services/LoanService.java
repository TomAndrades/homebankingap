package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Set;

public interface LoanService {

    void saveLoan(Loan loan);
    boolean existsLoanById(Long id);

    Loan getLoanById(Long id);

    LoanDTO getLoanDTOById(Long id);

    List<LoanDTO> getLoansDTO();


}
