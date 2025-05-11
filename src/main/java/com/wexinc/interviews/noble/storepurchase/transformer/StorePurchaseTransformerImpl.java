package com.wexinc.interviews.noble.storepurchase.transformer;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.wexinc.interviews.noble.storepurchase.dto.StorePurchaseDto;
import com.wexinc.interviews.noble.storepurchase.entities.StorePurchase;

import lombok.val;

@Service
public class StorePurchaseTransformerImpl implements StorePurchaseTransformer {

	@Override
	public StorePurchaseDto convertEntityToDto(StorePurchase source) {
		val result = new StorePurchaseDto();
		result.setStorePurchaseId(source.getStorePurchaseId());
		result.setDescription(source.getDescription());
		result.setPurchaseAmount(source.getPurchaseAmount());
		result.setTransactionDate(source.getTransactionDate().toString());
		return result;
	}

	@Override
	public StorePurchase convertDtoToEntity(StorePurchaseDto source) {
		StorePurchase result = new StorePurchase();
		result.setDescription(source.getDescription());
		result.setPurchaseAmount(source.getPurchaseAmount().setScale(2, RoundingMode.HALF_UP));
		result.setTransactionDate(LocalDateTime.parse(source.getTransactionDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return result;
	}

}
