package org.example.service;

import java.util.Timer;
import java.util.TimerTask;

public class InterestAccrualService {
    private final long PERIOD_MS = 30L * 24 * 60 * 60 * 1000;
    private final double interest;
    private double depositBalance;
    private final int accrualFrequency;

    public double getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(double depositBalance) {
        this.depositBalance = depositBalance;
    }

    public InterestAccrualService(double interest, double depositBalance, int accrualFrequency) {
        this.interest = interest;
        this.depositBalance = depositBalance;
        this.accrualFrequency = accrualFrequency;
    }

    public double applyInterest() {
        double interestAmount = depositBalance * interest / 100 / 365 * accrualFrequency;
        depositBalance += interestAmount;
        System.out.println("Percents applied. Current balance: " + depositBalance);
        return depositBalance;
    }

    public void startInterestAccrual() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                applyInterest();
            }
        }, PERIOD_MS, PERIOD_MS);
    }

    public void applyInterestPeriodically(int periods) {
        for (int i = 0; i < periods; i++) {
            applyInterest();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
