package com.wexinc.interviews.noble.storepurchase.processor;

import com.wexinc.interviews.noble.storepurchase.dto.StorePurchaseDto;

public interface StorePurchaseProcessor {
	StorePurchaseDto getStorePurchase(Long id, String countryCurrencyDesc);
	
	StorePurchaseDto createStorePurchase(StorePurchaseDto storePurchase);
}
