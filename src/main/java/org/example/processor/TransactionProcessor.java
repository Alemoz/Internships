package org.example.processor;

import org.example.domain.base.BankAccount;

import java.util.List;

public class TransactionProcessor {
    public void processTransaction(List<BankAccount> accounts, double amount) {
        for (BankAccount account : accounts) {
            account.withdraw(amount);
        }
    }
}
