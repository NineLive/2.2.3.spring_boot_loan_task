package ru.spring.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spring.spring_boot.SortIsBlockingException;
import ru.spring.spring_boot.models.Car;
import ru.spring.spring_boot.service.CarService;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cars")
public class CarsController {

    private final CarService carService;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "count", required = false) Integer count,
                        @RequestParam(value = "sortBy", required = false) String sortBy,
                        Model model) {
        carService.checkSortBlocking(sortBy);
        List<Car> carList = carService.getCarsByGivenCounter(count);
        model.addAttribute("cars", carService.sortByField(carList, sortBy));
        return "main/cars";
    }
}
