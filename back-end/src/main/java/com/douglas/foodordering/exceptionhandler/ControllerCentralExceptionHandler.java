package com.douglas.foodordering.exceptionhandler;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerCentralExceptionHandler {

	private Logger log = Logger.getLogger(ControllerCentralExceptionHandler.class);
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(HttpServletRequest request, Exception exception) throws Exception {
		// Log the error
		log.error("Request: " + request.getRequestURL() + " raised " + exception);
		
		// If the exception is annotated with @ResponseStatus re-throw it and let the framework handle it
		if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
			throw exception;
		
		// Otherwise return the error, stack-trace has been configured to be removed in application.properties
		return ResponseEntity.badRequest().body(request);
	}
}
