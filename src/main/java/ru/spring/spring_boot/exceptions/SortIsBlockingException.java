package ru.spring.spring_boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Sort is blocking")
public class SortIsBlockingException extends RuntimeException {
}
