package ru.spring.spring_boot.services;

public interface LoanService {
    public double calculateLoan(long id);

    public double getUserIncome(long id);
}
