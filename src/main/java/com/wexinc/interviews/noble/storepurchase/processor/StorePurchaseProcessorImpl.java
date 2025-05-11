package com.wexinc.interviews.noble.storepurchase.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wexinc.interviews.noble.storepurchase.dto.StorePurchaseDto;
import com.wexinc.interviews.noble.storepurchase.entities.StorePurchase;
import com.wexinc.interviews.noble.storepurchase.repository.StorePurchaseRepository;
import com.wexinc.interviews.noble.storepurchase.transformer.StorePurchaseTransformer;

import lombok.val;

@Service
public class StorePurchaseProcessorImpl implements StorePurchaseProcessor {

	@Autowired
	StorePurchaseRepository storePurchaseRepository;
	
	@Autowired
	StorePurchaseTransformer storePurchaseTransormer;
	
	@Autowired
	ExchangeRateProcessor exchangeRateProcessor;
	
	public StorePurchaseDto getStorePurchase(Long id, String countryCurrencyDesc) {
		val storePurchase = this.storePurchaseRepository.findById(id);
		if(!storePurchase.isPresent()) {
			return null;
		}
		
		val storePurchaseDto = this.storePurchaseTransormer.convertEntityToDto(storePurchase.get());
		if(!StringUtils.isEmpty(countryCurrencyDesc)) {
			val exchangeRate = exchangeRateProcessor.getExchangeRate(countryCurrencyDesc, storePurchase.get().getTransactionDate());
			storePurchaseDto.setCountryCurrencyDesc(countryCurrencyDesc);
			storePurchaseDto.setExchangeRate(exchangeRate);
			storePurchaseDto.setExchangePurchaseAmount(storePurchaseDto.getPurchaseAmount().multiply(new BigDecimal(exchangeRate)).setScale(2, RoundingMode.HALF_UP));	
		}
			
		return storePurchaseDto;
		
	}

	@Override
	public StorePurchaseDto createStorePurchase(StorePurchaseDto storePurchase) {
		StorePurchase storePurchaseEntity = this.storePurchaseTransormer.convertDtoToEntity(storePurchase);
		storePurchaseEntity = storePurchaseRepository.saveAndFlush(storePurchaseEntity);
		val result = this.storePurchaseTransormer.convertEntityToDto(storePurchaseEntity);
		return result;
	}
	
	
	
}
