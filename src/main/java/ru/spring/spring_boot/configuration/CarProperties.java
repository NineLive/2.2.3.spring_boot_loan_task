package ru.spring.spring_boot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "carproperties")
public class CarProperties {

    private int maxCar;

    private List<String> listOfDisabledSort;

    public List<String> getListOfDisabledSort() {
        return listOfDisabledSort;
    }

    public void setListOfDisabledSort(List<String> listOfDisabledSort) {
        this.listOfDisabledSort = listOfDisabledSort;
    }

    public int getMaxCar() {
        return maxCar;
    }

    public void setMaxCar(int maxCar) {
        this.maxCar = maxCar;
    }

}
