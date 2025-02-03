package org.example.Classes;

import org.example.Interfaces.InterestBearing;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class SavingsAccount extends BankAccount implements InterestBearing {
    private LocalDateTime depositOpeningDate;
    private double depositBalance=0;
    final double interest=14.5;           //ставка по депозиту
    final int accrualFrequency= 30;       //как часто начисляются проценты
    private final long PERIOD_MS = 30L * 24 * 60 * 60 * 1000; // 30 дней в миллисекундах

    public SavingsAccount(String accountHolder) {
        super(accountHolder);
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

    public void depositMoney(double amount){
        startInterestAccrual();
        setDepositOpeningDate(LocalDateTime.now());
        setDepositBalance(amount);
        System.out.println(getAccountHolder() + " deposited: " + amount + ". Current balance: " + getDepositBalance());
    }
    @Override
    public void withdrow(double amount) {
        if (getDepositBalance() >= amount && amount > 0) {
            setDepositBalance(getDepositBalance() - amount);
            System.out.println("Withdraw: " + amount + ". New balance: " + getBalance());
        } else if (amount < 0) {
            System.out.println("Error: Can`t withdraw less then 1");
        } else {
            System.out.println("Error: Not enough money on the bank account");
        }
    }
    @Override
    public void applyInterest() {
        double interestAmount = getDepositBalance() * interest / 100 / 365 * accrualFrequency;
        setDepositBalance(getDepositBalance() + interestAmount); // Добавляем проценты к балансу
        System.out.println("Percents applied. Current balance: " + getDepositBalance());
    }
    private void startInterestAccrual() {
        Timer timer = new Timer(true); // Даем фоновый поток (daemon)
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                applyInterest();
            }
        }, PERIOD_MS, PERIOD_MS); // Запускаем через 30 дней и далее каждый 30 дней
    }

    // Для тестирования: вызывайте этот метод вручную
    public void applyInterestPeriodically(int periods) {
        for (int i = 0; i < periods; i++) {
            applyInterest();
            try {
                Thread.sleep(5000); // Ждем 5 секунд перед следующим начислением
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
