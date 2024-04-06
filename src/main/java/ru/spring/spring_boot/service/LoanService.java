package ru.spring.spring_boot.service;

import ru.spring.spring_boot.JsonDataOfUserIncome;
import ru.spring.spring_boot.models.User;

import java.util.List;

public interface LoanService {
    public double calculateLoan(User user);

    public List<JsonDataOfUserIncome> getListJsonData();

    public User setUserIncome(List<JsonDataOfUserIncome> list, User user);
}
