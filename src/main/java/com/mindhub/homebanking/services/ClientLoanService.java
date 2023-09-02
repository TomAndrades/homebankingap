package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;

public interface ClientLoanService {
    void saveClientLoan(ClientLoan clientLoan);

    List<ClientLoan> getClientLoans();

    List<ClientLoanDTO> getClientLoansDTO();

    List<ClientLoan> getClientLoansByClient(Client client);

    List<ClientLoanDTO> getClientLoansDTOByClient(Client client);

    List<ClientLoanDTO> toDTO(List<ClientLoan> list);
}
