package com.wexinc.interviews.noble.storepurchase.tests.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wexinc.interviews.noble.storepurchase.dto.StorePurchaseDto;
import com.wexinc.interviews.noble.storepurchase.entities.StorePurchase;
import com.wexinc.interviews.noble.storepurchase.transformer.StorePurchaseTransformerImpl;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public class StorePurchaseTransformerTests {
	StorePurchaseTransformerImpl transformer = new StorePurchaseTransformerImpl();
	
	@Test
	public void CanConvertEntityToDto() {
		val now = LocalDateTime.now();
		val entity = new StorePurchase();
		entity.setStorePurchaseId(1L);
		entity.setDescription("TestDescription");
		entity.setPurchaseAmount(new BigDecimal(10));
		entity.setTransactionDate(now);
		
		val result = transformer.convertEntityToDto(entity);
		
		assertEquals(1L, result.getStorePurchaseId());
		assertEquals("TestDescription", result.getDescription());
		assertTrue(result.getPurchaseAmount().compareTo(new BigDecimal(10)) == 0);
		assertEquals(now.toString(), result.getTransactionDate());
	}
	
	@Test
	public void CanConvertDtoToEntity() {
		
		val dto = new StorePurchaseDto();
		dto.setStorePurchaseId(1L);
		dto.setDescription("TestDescription");
		dto.setPurchaseAmount(new BigDecimal(10));
		dto.setTransactionDate("2024-10-01 11:00:00");
		
		val result = transformer.convertDtoToEntity(dto);
		
		assertNull(result.getStorePurchaseId());
		assertEquals("TestDescription", result.getDescription());
		assertTrue(result.getPurchaseAmount().compareTo(new BigDecimal(10)) == 0);
		assertEquals("2024-10-01T11:00", result.getTransactionDate().toString());
	}
	
	@Test
	public void ConvertDtoToEntity_TransactionAmountRoundsToNearestCent() {
		val dto = new StorePurchaseDto();
		dto.setStorePurchaseId(1L);
		dto.setDescription("TestDescription");
		dto.setPurchaseAmount(new BigDecimal(10.005));
		dto.setTransactionDate("2024-10-01 11:00:00");
		
		val result1 = transformer.convertDtoToEntity(dto);
		
		assertEquals(10.01, result1.getPurchaseAmount().doubleValue());
		
		dto.setPurchaseAmount(new BigDecimal(10.004));
		
		val result2 = transformer.convertDtoToEntity(dto);
		
		assertEquals(10.00, result2.getPurchaseAmount().doubleValue());
	}
}
