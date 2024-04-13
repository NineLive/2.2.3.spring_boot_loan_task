package ru.spring.spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mystarter.userincome.IncomeClient;
import ru.spring.spring_boot.configuration.LoanProperties;
import ru.spring.spring_boot.exception.UserNotFoundException;
import ru.spring.spring_boot.model.User;

@Service
public class LoanServiceImp implements LoanService {
    public UserService userService;
    public LoanProperties loanProperties;
    public IncomeClient incomeClient;

    @Autowired
    public LoanServiceImp(UserService userService, LoanProperties loanProperties, IncomeClient userIncome) {
        this.userService = userService;
        this.loanProperties = loanProperties;
        this.incomeClient = userIncome;
    }

    @Override
    public double calculateLoan(long id) {
        if (userService.findById(id).isPresent()) {
            User user = userService.findById(id).get();
            double userIncome = incomeClient.getUserIncome(id);
            double carCoast = (user.getCar().isPresent()) ? user.getCar().get().getCost() : 0;
            double loanByUserIncomeForHalfYear = userIncome * 6;
            double loanByCarCost = carCoast * loanProperties.getPercentOfLoanByCarCost();
            if (carCoast > loanProperties.getMinimalCarCost() || userIncome > loanProperties.getMinimalIncome()) {
                return Math.max(loanByUserIncomeForHalfYear, loanByCarCost);
            }
            return 0;
        } else {
            throw new UserNotFoundException();
        }
    }
}
