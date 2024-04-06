package ru.spring.spring_boot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.spring.spring_boot.JsonDataOfUserIncome;
import ru.spring.spring_boot.configuration.LoanProperties;
import ru.spring.spring_boot.models.User;

import java.io.IOException;
import java.util.Arrays;
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
    @Transactional
    public double calculateLoan(User user) {
        User updatedUser = setUserIncome(getListJsonData(), user);
        double carCoast = (updatedUser.getCar().isPresent()) ? updatedUser.getCar().get().getCost() : 0;
        double userIncome = updatedUser.getIncome();
        double loanByUserIncomeForHalfYear = userIncome * 6;
        double loanByCarCost = carCoast * loanProperties.getPercentOfLoanByCarCost();
        if (carCoast > loanProperties.getMinimalCarCost() || userIncome > loanProperties.getMinimalIncome()) {
            return Math.max(loanByUserIncomeForHalfYear, loanByCarCost);
        }
        return 0;
    }

    @Override
    public List<JsonDataOfUserIncome> getListJsonData() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://66055cd12ca9478ea1801f2e.mockapi.io/api/users/income";
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        ResponseEntity<List<JsonDataOfUserIncome>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();

//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            RestTemplate restTemplate = new RestTemplate();
//            String url = "https://66055cd12ca9478ea1801f2e.mockapi.io/api/users/income";
//            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//            return objectMapper.readValue(response.getBody(), new TypeReference<>() { });
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
    }

    @Override
    @Transactional
    public User setUserIncome(List<JsonDataOfUserIncome> list, User user) {
        list.forEach(e -> {
            if (e.getId() == user.getId()) {
                user.setIncome(e.getIncome());
            }
        });
        return user;
    }
}
