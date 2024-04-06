package ru.spring.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.spring_boot.JsonData;
import ru.spring.spring_boot.models.User;
import ru.spring.spring_boot.service.LoanService;
import ru.spring.spring_boot.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;
    private final UserService userService;

    @Autowired
    public LoanController(LoanService loanService, UserService userService) {
        this.loanService = loanService;
        this.userService = userService;
    }

    @GetMapping()
    public double index(@RequestParam(value = "id", required = false) Long id) {
        User user = userService.findOne(id);
        double loan = loanService.calculateLoan(user);
        return loan;
    }
}
