package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    void saveAccount(Account account);
    List<AccountDTO> getAccountsDTO();
    List<Account> getAccounts();
    AccountDTO getAccountDTO(Long id);
    Optional<Account> getAccount(Long id);
    List<AccountDTO> getCurrentAccountsDTO(String clientEmail);
    Account createAccount(Client client);
    String generateAccountNumber();

}
