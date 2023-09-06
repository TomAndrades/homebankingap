package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ClientRepository clientRepository;


    @Override
    public void saveAccount(Account account) {
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
    public List<AccountDTO> getCurrentAccountsDTO(String clientEmail) {
        return getCurrentAccounts(clientEmail).stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public Set<Account> getCurrentAccounts(String clientEmail){
        return clientRepository.findByEmail(clientEmail).getAccounts();
    }
    @Override
    public boolean existsAccountByNumber(String number){
        return accountRepository.existsByNumber(number);
    }

    @Override
    public Account getAccountByNumber(String number){
        return accountRepository.findByNumber(number);
    }


    @Override
    public Account createAccount(Client client) {
        Account account = new Account(generateAccountNumber(), LocalDateTime.now(), 0.0);
        client.addAccount(account);
        saveAccount(account);
        return account;
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

