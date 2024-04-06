package ru.spring.spring_boot.service;

import ru.spring.spring_boot.JsonData;
import ru.spring.spring_boot.models.User;

import java.io.IOException;
import java.util.List;

public interface LoanService {
    public double calculateLoan(User user);
    public List<JsonData> getListJsonData();
    public void setUsersIncome(List<JsonData> list);
}
