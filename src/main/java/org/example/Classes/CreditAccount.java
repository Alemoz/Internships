package org.example.Classes;

import org.example.Interfaces.TransactionFee;
import org.example.Interfaces.TransactionValidator;

public class CreditAccount extends BankAccount implements TransactionValidator, TransactionFee  {
    private static final double creditLimit=-5000;
    private static final double fee=1;
    private static final double MAX_TRANSACTION_AMOUNT = 5_000;
    public CreditAccount(String accountHolder) {
        super(accountHolder);
    }

    @Override
    public boolean validate(double amount) {
        return amount <= MAX_TRANSACTION_AMOUNT;
    }

    @Override
    public void withdrow(double amount) {
        if(validate(amount)){
            if(getBalance()-amount-applyFee(amount)>=creditLimit){
                setBalance(getBalance()-amount-applyFee(amount));
                System.out.println("Withdraw: "+ amount+", fee: "+applyFee(amount)+". New balance: "+ getBalance());
            }else {
                System.out.println("Error: operation is getting out of your credit limit");
            }
        }else {
            System.out.println("Error: Transaction exceeds limit (10,000) for " + getAccountHolder());
        }
    }

    @Override
    public double applyFee(double amount){

        return amount*fee/100;
    }
}
