package ru.spring.spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.spring.spring_boot.UserIncomeDTO;
import ru.spring.spring_boot.configurations.LoanProperties;
import ru.spring.spring_boot.exceptions.UserNotFoundException;
import ru.spring.spring_boot.models.User;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LoanServiceImp implements LoanService {
    public UserService userService;
    public LoanProperties loanProperties;

    @Autowired
    public LoanServiceImp(UserService userService, LoanProperties loanProperties) {
        this.userService = userService;
        this.loanProperties = loanProperties;
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
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://66055cd12ca9478ea1801f2e.mockapi.io/api/users/income";
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        ResponseEntity<List<UserIncomeDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,null,
                new ParameterizedTypeReference<>() { });
        for (UserIncomeDTO element : responseEntity.getBody()) {
            if (id == element.getId()) {
                return element.getIncome();
            }
        }
        return 0;
    }
}
