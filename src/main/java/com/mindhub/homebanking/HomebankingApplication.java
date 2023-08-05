package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDateTime;


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
			Client client2 = new Client("Morgan", "Freeman", "mfreeman@mindhub.com");
			clientRepository.save(client);
			clientRepository.save(client2);

			//Crear las cuentas
			Account account1 = new Account("VIN001", LocalDateTime.now(),5000.0);
			Account account2 = new Account("VIN002",LocalDateTime.now().plusDays(1),7500.0);
			Account account3 = new Account("VIN003",LocalDateTime.now().minusDays(30),10.0);


			//Agregar las cuentas al cliente
			client.addAccount(account1);
			client.addAccount(account2);
			client2.addAccount(account3);
			//Guardar la cuenta
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

		};
	}

}
