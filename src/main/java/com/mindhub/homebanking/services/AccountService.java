package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountService {
    void saveAccount(Account account);
    List<AccountDTO> getAccountsDTO();
    List<Account> getAccounts();
    AccountDTO getAccountDTO(Long id);
    Optional<Account> getAccount(Long id);

    boolean existsAccountByNumber(String number);

    Account getAccountByNumber(String number);
    List<AccountDTO> getCurrentAccountsDTO(String clientEmail);

    Set<Account> getCurrentAccounts(String clientEmail);

    Account createAccount(Client client);
    String generateAccountNumber();

}
