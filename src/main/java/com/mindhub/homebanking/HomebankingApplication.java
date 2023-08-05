package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository){
		return args -> {
			//Crear el cliente
			Client client = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client);
			//Crear las cuentas
			Account account1 = new Account();
			Account account2 = new Account();
			account1.setNumber("VIN001");
			account1.setBalance(5000.0);
			account1.setCreationDate(LocalDate.now());

			account2.setNumber("VIN002");
			account2.setBalance(7500.0);
			account2.setCreationDate(LocalDate.now().plusDays(1));

			//Agregar las cuentas al cliente
			client.addAccount(account1);
			client.addAccount(account2);
			//Guardar la cuenta
			accountRepository.save(account1);
			accountRepository.save(account2);

		};
	}

}
