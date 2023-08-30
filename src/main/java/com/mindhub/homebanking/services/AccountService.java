package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountService {
    void saveAccount(Account account);
    List<AccountDTO> getAccountsDTO();
    List<Account> getAccounts();
    AccountDTO getAccountDTO(Long id);
    Optional<Account> getAccount(Long id);
    List<AccountDTO> getCurrentAccountsDTO(Authentication authentication);
    Set<Account> getCurrentAccounts(Authentication authentication);
    ResponseEntity<Object> createCurrentAccount(Authentication authentication);
    ResponseEntity<Object> createAccount(Client client);
    String generateAccountNumber();
}
