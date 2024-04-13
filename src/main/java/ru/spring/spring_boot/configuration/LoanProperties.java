package ru.spring.spring_boot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "loan")
public class LoanProperties {

    private int minimalIncome;

    private int minimalCarCost;

    private double percentOfLoanByCarCost;

    private String userIncomeUrl;
}

