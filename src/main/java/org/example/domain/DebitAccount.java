package org.example.domain;

import org.example.interfaces.validation.TransactionValidator;
import org.example.domain.base.BankAccount;

public class DebitAccount extends BankAccount implements TransactionValidator {
    private static final double MAX_TRANSACTION_AMOUNT = 10_000;

    public DebitAccount(String accountHolder) {
        super(accountHolder);
    }

    @Override
    public void withdraw(double amount) {
        if (amount < 0) {
            System.out.println("Can`t withdraw less then 1");
            return;
        }
        if (!validate(amount)) {
            System.out.println("Transaction exceeds limit (10,000) for " + getAccountHolder());
            return;
        }
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            System.out.println("Withdraw: " + amount + ". New balance: " + getBalance());
        } else {
            System.out.println("Not enough money on the bank account");
        }
    }


    @Override
    public boolean validate(double amount) {
        return amount <= MAX_TRANSACTION_AMOUNT;
    }
}
