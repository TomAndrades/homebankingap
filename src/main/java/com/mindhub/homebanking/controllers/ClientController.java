package com.mindhub.homebanking.controllers;

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
import org.springframework.web.bind.annotation.*;


import java.util.List;


import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){ return clientService.getClientsDTO(); }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){ return clientService.getClientDTO(id); }

    //Method to create a client validating that the email is not in the db
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> registerClient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password){
        if(firstName.isBlank() || lastName.isBlank() ){
            return new ResponseEntity<>("You need to enter a first name and last name", HttpStatus.FORBIDDEN);
        } else if (email.isBlank()) {
            return new ResponseEntity<>("You need to enter a valid email", HttpStatus.FORBIDDEN);
        } else if (password.isBlank()) {
            return new ResponseEntity<>("You need to enter a password", HttpStatus.FORBIDDEN);
        } else if (clientService.clientExist(email)) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        } else {
            Client client = clientService.register(firstName, lastName, email, password);
            accountService.createAccount(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return clientService.getClientDTOByEmail(authentication.getName());}

}
