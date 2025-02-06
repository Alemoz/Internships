package org.example.domain.base;

public abstract class BankAccount {
    protected long accountID;
    protected double balance;

    protected String accountHolder;

    public BankAccount(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountNumber) {
        this.accountID = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public abstract void withdraw(double amount);

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance = +amount;
        }
    }


}
