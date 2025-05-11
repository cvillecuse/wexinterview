package com.wexinc.interviews.noble.storepurchase.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import com.wexinc.interviews.noble.storepurchase.dto.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorDto> generateHttpStatusException(ResponseStatusException ex) {
		ErrorDto errorDTO = new ErrorDto();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(ex.getStatusCode().toString());
		errorDTO.setTime(new Date().toString());

		return new ResponseEntity<ErrorDto>(errorDTO, ex.getStatusCode());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>( errors, HttpStatus.BAD_REQUEST);
	}
}
