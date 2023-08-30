package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toList;
@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    @Lazy
    private ClientService clientService;

    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }
    @Override
    public List<AccountDTO> getAccountsDTO() {
        return getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public AccountDTO getAccountDTO(Long id) {
        return getAccount(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<AccountDTO> getCurrentAccountsDTO(Authentication authentication) {
        return getCurrentAccounts(authentication).stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public Set<Account> getCurrentAccounts(Authentication authentication) {
        return clientService.getCurrentClient(authentication).getAccounts();
    }

    @Override
    public ResponseEntity<Object> createCurrentAccount(Authentication authentication) {
        if (authentication != null){
            Client client = clientService.getCurrentClient(authentication);
            return createAccount(client);
        } else {
            return new ResponseEntity<>("You must need to be logged before create an account",HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<Object> createAccount(Client client){
        if (client.getAccounts().size() == 3){
            return new ResponseEntity<>("You already have 3 accounts",HttpStatus.FORBIDDEN);
        }
        Account account = new Account( generateAccountNumber(), LocalDateTime.now(), 0.0);
        client.addAccount(account);
        saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public String generateAccountNumber() {
        int min = 0;
        int max = 9999999;
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = "VIN-" + random.nextInt(max - min) + min;
        } while (null != accountRepository.findByNumber(accountNumber));
        return accountNumber;

    }
}
