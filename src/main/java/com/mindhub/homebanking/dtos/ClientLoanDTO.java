package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {

    private Long id;
    private Long loanId;
    private String name;
    private Double amount;
    private Integer payment;

    public ClientLoanDTO (ClientLoan clientLoan){
        Loan loan = clientLoan.getLoan();
        this.id = clientLoan.getId();
        this.loanId = loan.getId();
        this.name = loan.getName();
        this.amount = clientLoan.getAmount();
        this.payment = clientLoan.getPayments();

    }

    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayment() {
        return payment;
    }
}
