package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {

    @Autowired
    private ClientLoanRepository clientLoanRepository;
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

}
