package com.douglas.foodordering.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ControllerException extends RuntimeException {
	public ControllerException(String message) {
        super(message);
    }
}
