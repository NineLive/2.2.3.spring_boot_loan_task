package ru.spring.spring_boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found user with given id")
public class UserNotFoundException extends RuntimeException {
}
