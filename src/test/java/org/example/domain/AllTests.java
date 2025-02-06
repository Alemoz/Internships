package org.example.domain;

import org.example.domain.base.BankAccount;
import org.example.processor.TransactionProcessor;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AllTests {
    @Test
    public void debitAccountTestWithdraw() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DebitAccount account1 = new DebitAccount("Alex");
        DebitAccount account2 = new DebitAccount("Jame");
        DebitAccount account3 = new DebitAccount("Nikol");
        DebitAccount account4 = new DebitAccount("Ann");
        account1.setBalance(231230);
        account2.setBalance(100);
        account3.setBalance(0);
        account4.setBalance(2);
        account1.withdraw(1230);
        account2.withdraw(100);
        account3.withdraw(5000);
        account4.withdraw(-20);
        List<String> expected = Arrays.asList(
                "Withdraw: 1230.0. New balance: 230000.0",
                "Withdraw: 100.0. New balance: 0.0",
                "Not enough money on the bank account",
                "Can`t withdraw less then 1"
        );
        List<String> actual = Arrays.asList(outContent.toString().trim().split("\\r?\\n"));
        assertEquals(expected, actual);
        System.setOut(originalOut);
    }

    @Test
    public void creditAccountTestDeposit() {
        CreditAccount account1 = new CreditAccount("Alex");
        CreditAccount account2 = new CreditAccount("Jame");
        CreditAccount account3 = new CreditAccount("Nikol");
        CreditAccount account4 = new CreditAccount("Ann");
        List<Double> expected = List.of(231230.0, 10.0, 0.0, 0.0);
        account1.deposit(231230);
        account2.deposit(10);
        account3.deposit(0);
        account4.deposit(-100);
        List<Double> actual = List.of(account1.getBalance(), account2.getBalance(), account3.getBalance(), account4.getBalance());
        assertEquals(expected, actual);
    }

    @Test
    public void creditAccountTestWithdraw() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CreditAccount account1 = new CreditAccount("Alex");
        CreditAccount account2 = new CreditAccount("Jame");
        CreditAccount account3 = new CreditAccount("Nikol");
        CreditAccount account4 = new CreditAccount("Ann");
        System.out.flush();
        account1.setBalance(231230);
        account2.setBalance(10);
        account3.setBalance(0);
        account4.setBalance(-5000);
        account1.withdraw(231230);
        account2.withdraw(100);
        account3.withdraw(5000);
        account4.withdraw(20);
        List<String> expected = Arrays.asList(
                "Transaction exceeds limit (10,000) for Alex",
                "Withdraw: 100.0, fee: 1.0. New balance: -91.0",
                "Operation is getting out of your credit limit",
                "Operation is getting out of your credit limit"
        );
        List<String> actual = Arrays.asList(outContent.toString().trim().split("\\r?\\n"));
        assertEquals(expected, actual);
        System.setOut(originalOut);
    }

    @Test
    public void transactionProcessorTest() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        List<BankAccount> accounts = List.of(
                new DebitAccount("Alex"),
                new CreditAccount("Jame"),
                new SavingsAccount("Charlie")
        );
        TransactionProcessor processor = new TransactionProcessor();
        processor.processTransaction(accounts, 35510);
        processor.processTransaction(accounts, 2341);
        processor.processTransaction(accounts, 34);
        List<String> expected = Arrays.asList(
                "Transaction exceeds limit (10,000) for Alex",
                "Transaction exceeds limit (10,000) for Jame",
                "Not enough money on the bank account",
                "Not enough money on the bank account",
                "Withdraw: 2341.0, fee: 23.41. New balance: -2364.41",
                "Not enough money on the bank account",
                "Not enough money on the bank account",
                "Withdraw: 34.0, fee: 0.34. New balance: -2398.75",
                "Not enough money on the bank account"
        );
        List<String> actual = Arrays.asList(outContent.toString().trim().split("\\r?\\n"));
        assertEquals(expected, actual);
        System.setOut(originalOut);
    }

    @Test
    public void savingAccountTest() {
        SavingsAccount account = new SavingsAccount("Jane");
        account.depositMoney(3000);
        account.applyInterestPeriodically(2);
        assertEquals(3071.93, account.getDepositBalance(), 2);
    }
}