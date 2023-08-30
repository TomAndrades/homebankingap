package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    void saveClient(Client client);
    List<ClientDTO> getClientsDTO();
    List<Client> getClients();
    ClientDTO getClientDTO(Long id);
    Optional<Client> getClient(Long id);
    ResponseEntity<Object> register(String firstName, String lastName,String email, String password);
    ClientDTO getCurrentClientDTO(Authentication authentication);
    Client getCurrentClient(Authentication authentication);

}
