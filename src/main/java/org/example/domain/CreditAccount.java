package org.example.domain;

import org.example.interfaces.TransactionFee;
import org.example.interfaces.validation.TransactionValidator;
import org.example.domain.base.BankAccount;

public class CreditAccount extends BankAccount implements TransactionValidator, TransactionFee {
    private static final double CREDIT_LIMIT = -5000;
    private static final double FEE = 1;
    private static final double MAX_TRANSACTION_AMOUNT = 5_000;

    public CreditAccount(String accountHolder) {
        super(accountHolder);
    }

    @Override
    public boolean validate(double amount) {
        return amount <= MAX_TRANSACTION_AMOUNT;
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
        if (getBalance() - amount - applyFee(amount) >= CREDIT_LIMIT) {
            setBalance(getBalance() - amount - applyFee(amount));
            System.out.println("Withdraw: " + amount + ", fee: " + applyFee(amount) + ". New balance: " + getBalance());
        } else {
            System.out.println("Operation is getting out of your credit limit");
        }
    }

    @Override
    public double applyFee(double amount) {
        return amount * FEE / 100;
    }
}
