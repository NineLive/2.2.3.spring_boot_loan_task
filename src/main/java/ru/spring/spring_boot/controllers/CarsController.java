package ru.spring.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.spring_boot.exceptions.SortIsBlockingException;
import ru.spring.spring_boot.models.Car;
import ru.spring.spring_boot.services.CarService;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarsController {

    private final CarService carService;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public String showCars(@RequestParam(value = "count", required = false) Integer count,
                           @RequestParam(value = "sortBy", required = false) String sortBy,
                           Model model) {
        carService.checkSortBlocking(sortBy);
        List<Car> carList = carService.getCarsByGivenCounter(count);
        model.addAttribute("cars", carService.sortByField(carList, sortBy));
        return "main/cars";
    }

    @ExceptionHandler(value = SortIsBlockingException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handler(){
        return "Ooops, BAD REQUEST!!! This sorting is locked";
    }
}
