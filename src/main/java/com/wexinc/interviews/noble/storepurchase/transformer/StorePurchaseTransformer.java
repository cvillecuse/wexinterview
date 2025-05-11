package com.wexinc.interviews.noble.storepurchase.transformer;

import com.wexinc.interviews.noble.storepurchase.dto.StorePurchaseDto;
import com.wexinc.interviews.noble.storepurchase.entities.StorePurchase;

public interface StorePurchaseTransformer {

	StorePurchaseDto convertEntityToDto(StorePurchase source);
	StorePurchase convertDtoToEntity(StorePurchaseDto source);
}
