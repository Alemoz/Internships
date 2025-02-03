package org.example.Classes;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Tests {

    @Test
    public void creditAccountTestDeposit() {
        CreditAccount account1 = new CreditAccount("Alex");
        CreditAccount account2 = new CreditAccount("Jame");
        CreditAccount account3 = new CreditAccount("Nikol");
        CreditAccount account4 = new CreditAccount("Ann");

        List<String> expected = new ArrayList<String>();
        expected.add("Operation succeeded");//1
        expected.add("Operation succeeded");//2
        expected.add("Operation failed! Can`t deposit less then 1");//3
        expected.add("Operation failed! Can`t deposit less then 1");//4

        List<String> actual = new ArrayList<>();
        actual.add(account1.deposit(231230));
        actual.add(account2.deposit(10));
        actual.add(account3.deposit(0));
        actual.add(account4.deposit(-100));

        assertEquals(expected, actual);
    }
    @Test
    public void creditAccountTestWithdrow() {
        PrintStream originalOut = System.out;
        // Перехватываем вывод в консоль
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        CreditAccount account1 = new CreditAccount("Alex");
        CreditAccount account2 = new CreditAccount("Jame");
        CreditAccount account3 = new CreditAccount("Nikol");
        CreditAccount account4 = new CreditAccount("Ann");
//        System.out.println(account1.getAccountHolder());
        System.out.flush();

        account1.setBalance(231230);
        account2.setBalance(10);
        account3.setBalance(0);
        account4.setBalance(-5000);

        account1.withdrow(231230);
        account2.withdrow(100);
        account3.withdrow(5000);
        account4.withdrow(20);

        List<String> expected = Arrays.asList(
                "Error: Transaction exceeds limit (10,000) for Alex",
                "Withdraw: 100.0, fee: 1.0. New balance: -91.0",
                "Error: operation is getting out of your credit limit",
                "Error: operation is getting out of your credit limit"
        );
        List<String> actual = Arrays.asList(outContent.toString().trim().split("\\r?\\n"));
        assertEquals(expected, actual);



        System.setOut(originalOut);
    }
    @Test
    public void debitAccountTestWithdraw(){
        PrintStream originalOut = System.out;
        // Перехватываем вывод в консоль
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

        account1.withdrow(1230);
        account2.withdrow(100);
        account3.withdrow(5000);
        account4.withdrow(-20);

        List<String> expected = Arrays.asList(
                "Withdraw: 1230.0. New balance: 230000.0",
                "Withdraw: 100.0. New balance: 0.0",
                "Error: Not enough money on the bank account",
                "Error: Can`t withdraw less then 1"
        );

        List<String> actual = Arrays.asList(outContent.toString().trim().split("\\r?\\n"));
        assertEquals(expected, actual);

        System.setOut(originalOut);
    }

    @Test
    public void TransactionProcessorTest(){
        PrintStream originalOut = System.out;
        // Перехватываем вывод в консоль
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new DebitAccount("Alex"));
        accounts.add(new CreditAccount("Jame"));
        accounts.add(new SavingsAccount("Charlie"));

        TransactionProcessor processor = new TransactionProcessor();
        processor.processTransaction(accounts, 35510);
        processor.processTransaction(accounts, 2341);
        processor.processTransaction(accounts, 34);

        List<String> expected = Arrays.asList(
                "Error: Transaction exceeds limit (10,000) for Alex",
                "Error: Transaction exceeds limit (10,000) for Jame",
                "Error: Not enough money on the bank account",
                "Error: Not enough money on the bank account",
                "Withdraw: 2341.0, fee: 23.41. New balance: -2364.41",
                "Error: Not enough money on the bank account",
                "Error: Not enough money on the bank account",
                "Withdraw: 34.0, fee: 0.34. New balance: -2398.75",
                "Error: Not enough money on the bank account"
        );
        List<String> actual = Arrays.asList(outContent.toString().trim().split("\\r?\\n"));
        assertEquals(expected, actual);
        System.setOut(originalOut);
    }
    @Test
    public void savingAccountTest(){
        SavingsAccount account = new SavingsAccount("Jane");
        account.depositMoney(3000);
        //почему-то через поток не хочет работать, много думать тяжко, мб еще поправлю
//        try {
//            Thread.sleep(2 * 5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        //поэтому для показа работоспособности реализован метод который дважды
        //"в ручную" вызывается
        account.applyInterestPeriodically(2);

        System.out.println("Balance after 2 periods: " + account.getDepositBalance());
    }
}