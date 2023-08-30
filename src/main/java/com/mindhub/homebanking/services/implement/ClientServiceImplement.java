package com.mindhub.homebanking.services.implement;


import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public void saveClient(Client client){
        clientRepository.save(client);
    }
    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }
    @Override
    public List<ClientDTO> getClientsDTO() {
        return getClients().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public ClientDTO getClientDTO(Long id) {
        return getClient(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public Optional<Client> getClient(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public ResponseEntity<Object> register(String firstName, String lastName, String email, String password) {
        if(firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(clientRepository.findByEmail(email) != null){
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        saveClient(client);
        accountService.createAccount(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ClientDTO getCurrentClientDTO(Authentication authentication) {
        return new ClientDTO(getCurrentClient(authentication));
    }

    @Override
    public Client getCurrentClient(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }
}
