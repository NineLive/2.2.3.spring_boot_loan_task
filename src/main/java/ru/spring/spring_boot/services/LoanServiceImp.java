package ru.spring.spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mystarter.userincome.UserIncomeDTO;
import ru.spring.spring_boot.configurations.LoanProperties;
import ru.spring.spring_boot.exceptions.UserNotFoundException;
import ru.spring.spring_boot.models.User;
import ru.mystarter.userincome.UserIncome;

@Service
@Transactional(readOnly = true)
public class LoanServiceImp implements LoanService {
    public UserService userService;
    public LoanProperties loanProperties;
    public UserIncome userIncome;

    @Autowired
    public LoanServiceImp(UserService userService, LoanProperties loanProperties, UserIncome userIncome) {
        this.userService = userService;
        this.loanProperties = loanProperties;
        this.userIncome = userIncome;
    }

    @Override
    public double calculateLoan(long id) {
        if (userService.findById(id).isPresent()) {
            User user = userService.findById(id).get();
            double userIncome = getUserIncome(id);
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

    @Override
    public double getUserIncome(long id) {
        for (UserIncomeDTO userIncomeDTO : userIncome.getListUserIncome()) {
            if (id == userIncomeDTO.getId()) {
                return userIncomeDTO.getIncome();
            }
        }
        return 0;
    }
}
