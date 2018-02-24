package com.tienda.online.controller;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.online.model.dto.response.ErrorResponse;

@RestController
public class BaseController {

	public BaseController() {
	
	}
	
	// Se usa notaciones para las llamadas a los metodos
		@ResponseStatus(HttpStatus.NOT_FOUND)
		@ExceptionHandler(NoSuchElementException.class)
		public ErrorResponse return404(NoSuchElementException ex) {
			return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
		}
		
		@ResponseStatus(HttpStatus.CONFLICT)
		@ExceptionHandler(DataIntegrityViolationException.class)
		public ErrorResponse return409(DataIntegrityViolationException ex) {
			return new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
		}

}
