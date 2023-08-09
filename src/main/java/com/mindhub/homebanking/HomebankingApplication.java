package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner init(ClientRepository clientRepository, AccountRepository accountRepository,
								  TransactionRepository transactionRepository, LoanRepository loanRepository,
								  ClientLoanRepository clientLoanRepository){
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

			//Crear las transacciones
			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 1230.0, "Pago de haberes");
			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -230.0, "Luz");
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -10.0, "Gas");
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 500.0, "Aguinaldo");
			Transaction transaction5 = new Transaction(TransactionType.DEBIT, -150.0, "Supermercado Mayorista");
			Transaction transaction6 = new Transaction(TransactionType.DEBIT, -100.0, "Extraccion cajero");
			Transaction transaction7 = new Transaction(TransactionType.DEBIT, -500.0, "Juegos Steam");
			Transaction transaction8 = new Transaction(TransactionType.DEBIT, -1000.0, "Agua");
			Transaction transaction9 = new Transaction(TransactionType.CREDIT, 1300.0, "Pago de haberes");
			Transaction transaction10 = new Transaction(TransactionType.DEBIT, -1100.0, "Alquiler");
			Transaction transaction11 = new Transaction(TransactionType.CREDIT, 530.0, "Pago de haberes");

			//Agregar las transacciones
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account2.addTransaction(transaction5);
			account2.addTransaction(transaction6);
			account2.addTransaction(transaction7);
			account2.addTransaction(transaction8);
			account3.addTransaction(transaction9);
			account3.addTransaction(transaction10);
			account3.addTransaction(transaction11);

			//Agregar las cuentas al cliente
			client.addAccount(account1);
			client.addAccount(account2);
			client2.addAccount(account3);

			//Guardar la cuenta
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);
			transactionRepository.save(transaction9);
			transactionRepository.save(transaction10);
			transactionRepository.save(transaction11);

			Loan mortgage = new Loan("Mortgage", 500000.0, List.of(12,24,36,48,60));
			Loan personal = new Loan("Personal", 100000.0, List.of(6,12,24));
			Loan automotive = new Loan("Automotive", 500000.0, List.of(6,12,24,36));

			loanRepository.save(mortgage);
			loanRepository.save(personal);
			loanRepository.save(automotive);

			ClientLoan clientLoan1 = new ClientLoan(400000.0,60);
			ClientLoan clientLoan2 = new ClientLoan(50000.0,12);
			ClientLoan clientLoan3 = new ClientLoan(100000.0,24);
			ClientLoan clientLoan4 = new ClientLoan(200000.0,36);

			client.addLoan(clientLoan1);
			client.addLoan(clientLoan2);
			client2.addLoan(clientLoan3);
			client2.addLoan(clientLoan4);

			mortgage.addLoan(clientLoan1);
			personal.addLoan(clientLoan2);
			personal.addLoan(clientLoan3);
			automotive.addLoan(clientLoan4);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);





		};
	}

}
