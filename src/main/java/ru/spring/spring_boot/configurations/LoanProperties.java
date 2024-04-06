package ru.spring.spring_boot.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "loan")
public class LoanProperties {
    private int minimalIncome;
    private int minimalCarCost;

    private double percentOfLoanByCarCost;

    public double getPercentOfLoanByCarCost() {
        return percentOfLoanByCarCost;
    }

    public void setPercentOfLoanByCarCost(double percentOfLoanByCarCost) {
        this.percentOfLoanByCarCost = percentOfLoanByCarCost;
    }

    public int getMinimalIncome() {
        return minimalIncome;
    }

    public void setMinimalIncome(int minimalIncome) {
        this.minimalIncome = minimalIncome;
    }

    public int getMinimalCarCost() {
        return minimalCarCost;
    }

    public void setMinimalCarCost(int minimalCarCost) {
        this.minimalCarCost = minimalCarCost;
    }
}
