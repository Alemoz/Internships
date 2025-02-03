package org.example.Classes;

import org.example.Interfaces.TransactionValidator;

public class DebitAccount extends BankAccount implements TransactionValidator {
    private static final double MAX_TRANSACTION_AMOUNT = 10_000;
    public DebitAccount(String accountHolder) {
        super(accountHolder);
    }

    @Override
    public void withdrow(double amount) {
        if(validate(amount)){
            if(getBalance()>=amount&&amount>0){
                setBalance(getBalance()-amount);
                System.out.println("Withdraw: "+ amount+". New balance: "+ getBalance());
            }else if(amount<0) {
                System.out.println("Error: Can`t withdraw less then 1");
            }else {
                System.out.println("Error: Not enough money on the bank account");
            }
        }else {
            System.out.println("Error: Transaction exceeds limit (10,000) for " + getAccountHolder());
        }
    }


    @Override
    public boolean validate(double amount) {
        return amount <= MAX_TRANSACTION_AMOUNT;
    }
}
