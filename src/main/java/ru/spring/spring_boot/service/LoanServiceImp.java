package ru.spring.spring_boot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.spring_boot.JsonData;
import ru.spring.spring_boot.configuration.LoanProperties;
import ru.spring.spring_boot.models.User;
import ru.spring.spring_boot.repositories.CarRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LoanServiceImp implements LoanService {
    public CarRepository carRepository;
    public UserService userService;
    public LoanProperties loanProperties;

    @Autowired
    public LoanServiceImp(CarRepository carRepository, UserService userService, LoanProperties loanProperties) {
        this.carRepository = carRepository;
        this.userService = userService;
        this.loanProperties = loanProperties;
    }

    @Override
    @Transactional
    public double calculateLoan(User user) {
        setUsersIncome(getListJsonData());
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isEmpty()) {
            return 0;
        }

        double userIncome = user.getIncome();
        double carCoast = user.getCar().getCost();
        double loanByUserIncomeForHalfYear = userIncome * 6;
        double loanByCarCost = carCoast * loanProperties.getPercentOfLoanByCarCost();
        if (carCoast > loanProperties.getMinimalCarCost() || userIncome > loanProperties.getMinimalIncome()) {
            return Math.max(loanByUserIncomeForHalfYear, loanByCarCost);
        }
        return 0;
    }

    @Override
    public List<JsonData> getListJsonData() {
        try{
            URL url = new URL("https://66055cd12ca9478ea1801f2e.mockapi.io/api/users/income");
            ObjectMapper objectMapper = new ObjectMapper();
            List<JsonData> listCar = objectMapper.readValue(url, new TypeReference<>() { });
            return listCar;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void setUsersIncome(List<JsonData> list) {
        for (JsonData jsonData : list) {
            User user = userService.findOne(jsonData.getId());
            user.setIncome(jsonData.getIncome());
            userService.update(jsonData.getId(), user);
            System.out.println("user обновлен id = " + jsonData.getId());
        }
    }
}
