package com.mitocode.proyectofinal.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mitocode.proyectofinal.dtos.ApiErrorDto;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", now.format(formatter));
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorDto errorDto = new ApiErrorDto(
	            HttpStatus.BAD_REQUEST,
	            ex.getMessage(),
	            Arrays.asList("")
	    );
		return new ResponseEntity<>(errorDto, headers, status);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException
			(ResourceNotFoundException exception, WebRequest webRequest){
        String errors = exception.getMessage();

        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.NOT_FOUND,
        		"Producto no encontrado",
        		Arrays.asList(errors));
		
		return new ResponseEntity<>(apiErrorDto, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorDto> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		ApiErrorDto apiErrorDto = new ApiErrorDto(
	            HttpStatus.BAD_REQUEST,
	            ex.getMessage(),
	            ex.getConstraintViolations().stream().map(x -> x.getMessage()).toList()
	    );
        return new ResponseEntity<ApiErrorDto>(apiErrorDto, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(value = {Throwable.class})
	protected ResponseEntity<ApiErrorDto> handleThrowable (Throwable ex, WebRequest request) {
	    ApiErrorDto apiErrorDto = new ApiErrorDto(
	            HttpStatus.BAD_REQUEST,
	            "Error invocando al servidor",
	            Arrays.asList(ex.getMessage())
	    );
	    return new ResponseEntity<ApiErrorDto>(apiErrorDto, HttpStatus.BAD_REQUEST);
	}
}
