package ru.spring.spring_boot.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "carproperties")
public class CarProperties {

    private int maxCar;

    private List<String> listOfDisabledSort;
}
