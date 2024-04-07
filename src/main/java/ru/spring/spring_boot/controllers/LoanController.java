package ru.spring.spring_boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spring.spring_boot.exceptions.UserNotFoundException;
import ru.spring.spring_boot.services.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping()
    public double showLoan(@RequestParam("id") Long id) {
        return loanService.calculateLoan(id);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> handle(){
        String body = "Oooops, NOT FOUND!!! User with this ID not found";
        return ResponseEntity.badRequest().body(body);
    }
}
