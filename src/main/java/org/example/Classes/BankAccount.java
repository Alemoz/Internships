package org.example.Classes;

public abstract class BankAccount {
    private static int accountCounter = 0;
    private int accountID;
    private double balance;

    private String accountHolder;

    public BankAccount(String accountHolder){
        this.accountHolder=accountHolder;
        accountID = ++accountCounter;
    }
    public int getAccountID() {
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

    public abstract void withdrow(double amount);

    public String deposit(double amount){
        if(amount>0){
            this.balance=+amount;
            return "Operation succeeded";
        }
        else {
            return "Operation failed! Can`t deposit less then 1";
        }
    }


}
