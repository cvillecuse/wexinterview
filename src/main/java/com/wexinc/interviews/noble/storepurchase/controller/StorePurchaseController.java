package com.wexinc.interviews.noble.storepurchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wexinc.interviews.noble.storepurchase.dto.StorePurchaseDto;
import com.wexinc.interviews.noble.storepurchase.processor.StorePurchaseProcessor;

import jakarta.validation.Valid;
import lombok.val;

@RestController
public class StorePurchaseController {
	@Autowired
	private StorePurchaseProcessor storePurchaseProcessor;
	
	@PostMapping(path = "storepurchase")
	public StorePurchaseDto createStorePurchase(@Valid @RequestBody StorePurchaseDto request) {
		val response = this.storePurchaseProcessor.createStorePurchase(request);
		return response;
	}
	
	@GetMapping(path = "storepurchase/{id}")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public StorePurchaseDto getStorePurchase(@PathVariable Long id, @RequestParam(value = "countryCurrencyDesc", required = false) String countryCurrencyDesc ) {
		val response = this.storePurchaseProcessor.getStorePurchase(id, countryCurrencyDesc);
		if(response == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could Not Find Store Purchase");
		}
		return response;
	}
	
}
