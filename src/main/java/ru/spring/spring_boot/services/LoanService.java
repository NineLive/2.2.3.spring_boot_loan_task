package ru.spring.spring_boot.services;

import ru.spring.spring_boot.UserIncomeDTO;

import java.util.List;

public interface LoanService {
    public double calculateLoan(long id);

    public List<UserIncomeDTO> getListJsonData();
}
