package org.example.domain;

import org.example.service.InterestAccrualService;
import org.example.interfaces.InterestBearing;
import org.example.domain.base.BankAccount;

import java.time.LocalDateTime;

public class SavingsAccount extends BankAccount implements InterestBearing {
    private LocalDateTime depositOpeningDate;
    private double depositBalance = 0;
    private static final double INTEREST_RATE = 14.5;
    private static final int ACCRUAL_FREQUENCY = 30;

    private InterestAccrualService interestAccrualService;

    public SavingsAccount(String accountHolder) {
        super(accountHolder);
        this.interestAccrualService = new InterestAccrualService(INTEREST_RATE, getDepositBalance(), ACCRUAL_FREQUENCY);
    }

    public double getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(double depositBalance) {
        this.depositBalance = depositBalance;
    }

    public LocalDateTime getDepositOpeningDate() {
        return depositOpeningDate;
    }

    public void setDepositOpeningDate(LocalDateTime depositOpeningDate) {
        this.depositOpeningDate = depositOpeningDate;
    }

    public void depositMoney(double amount) {
        interestAccrualService.setDepositBalance(amount);
        interestAccrualService.startInterestAccrual();
        setDepositOpeningDate(LocalDateTime.now());
        System.out.println(getAccountHolder() + " deposited: " + amount + ". Current balance: " + getDepositBalance());
    }

    @Override
    public void withdraw(double amount) {
        if (amount < 0) {
            System.out.println("Can`t withdraw less then 1");

        } else if (getDepositBalance() >= amount) {
            setDepositBalance(getDepositBalance() - amount);
            System.out.println("Withdraw: " + amount + ". New balance: " + getBalance());
        } else {
            System.out.println("Not enough money on the bank account");
        }
    }

    @Override
    public void applyInterest() {
        setDepositBalance(interestAccrualService.applyInterest());
    }

    public void applyInterestPeriodically(int periods) {
        for (int i = 0; i < periods; i++) setDepositBalance(interestAccrualService.applyInterest());
    }
}
