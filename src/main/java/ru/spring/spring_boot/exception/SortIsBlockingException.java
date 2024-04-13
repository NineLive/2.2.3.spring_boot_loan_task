package ru.spring.spring_boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Sort is blocked")
public class SortIsBlockingException extends RuntimeException {
}
