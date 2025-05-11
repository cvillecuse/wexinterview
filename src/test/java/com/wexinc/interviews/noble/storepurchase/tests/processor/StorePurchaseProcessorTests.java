package com.wexinc.interviews.noble.storepurchase.tests.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wexinc.interviews.noble.storepurchase.dto.StorePurchaseDto;
import com.wexinc.interviews.noble.storepurchase.entities.StorePurchase;
import com.wexinc.interviews.noble.storepurchase.processor.ExchangeRateProcessor;
import com.wexinc.interviews.noble.storepurchase.processor.StorePurchaseProcessorImpl;
import com.wexinc.interviews.noble.storepurchase.repository.StorePurchaseRepository;
import com.wexinc.interviews.noble.storepurchase.transformer.StorePurchaseTransformer;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public class StorePurchaseProcessorTests {
	@Mock
	StorePurchaseRepository storePurchaseRepository;
	
	@Mock
	StorePurchaseTransformer storePurchaseTransformer;
	
	@Mock
	ExchangeRateProcessor exchangeRateProcessor;
	
	@InjectMocks
	StorePurchaseProcessorImpl storePurchaseProcessor;
	
	@Test
	public void CanGetStorePurchase() {
		val now = LocalDateTime.now();
		val storePurchase = new StorePurchase();
		storePurchase.setDescription("Description1");
		storePurchase.setPurchaseAmount(new BigDecimal(10));
		storePurchase.setStorePurchaseId(1L);
		storePurchase.setTransactionDate(now);
		
		when(this.storePurchaseRepository.findById(eq(1L))).thenReturn(Optional.of(storePurchase));
		
		val storePurchaseDto = new StorePurchaseDto();
		storePurchaseDto.setDescription("Description1");
		storePurchaseDto.setPurchaseAmount(new BigDecimal(10));
		storePurchaseDto.setStorePurchaseId(1L);
		storePurchaseDto.setTransactionDate(now.toString());
		when(storePurchaseTransformer.convertEntityToDto(any(StorePurchase.class))).thenReturn(storePurchaseDto);
		
		when(exchangeRateProcessor.getExchangeRate(any(String.class), any(LocalDateTime.class))).thenReturn(1.5);
		
		val result = this.storePurchaseProcessor.getStorePurchase(1L, "Canada-Dollar");
		
		assertEquals(1L, result.getStorePurchaseId());
		assertEquals("Description1", result.getDescription());
		assertEquals(now.toString(), result.getTransactionDate());
		assertTrue(result.getPurchaseAmount().compareTo(new BigDecimal(10)) == 0);
		assertEquals("Canada-Dollar", result.getCountryCurrencyDesc());
		assertEquals(1.5, result.getExchangeRate());
		assertTrue(result.getExchangePurchaseAmount().compareTo(new BigDecimal(15.00)) == 0);
		
	}
	
	@Test
	public void GetStorePurchaseNullCountryCurrencyDesc() {
		val now = LocalDateTime.now();
		val storePurchase = new StorePurchase();
		storePurchase.setDescription("Description1");
		storePurchase.setPurchaseAmount(new BigDecimal(10));
		storePurchase.setStorePurchaseId(1L);
		storePurchase.setTransactionDate(now);
		
		when(this.storePurchaseRepository.findById(eq(1L))).thenReturn(Optional.of(storePurchase));
		
		val storePurchaseDto = new StorePurchaseDto();
		storePurchaseDto.setDescription("Description1");
		storePurchaseDto.setPurchaseAmount(new BigDecimal(10));
		storePurchaseDto.setStorePurchaseId(1L);
		storePurchaseDto.setTransactionDate(now.toString());
		when(storePurchaseTransformer.convertEntityToDto(any(StorePurchase.class))).thenReturn(storePurchaseDto);
		
		val result = this.storePurchaseProcessor.getStorePurchase(1L, null);
		
		assertEquals(1L, result.getStorePurchaseId());
		assertEquals("Description1", result.getDescription());
		assertEquals(now.toString(), result.getTransactionDate());
		assertTrue(result.getPurchaseAmount().compareTo(new BigDecimal(10)) == 0);
		assertNull(result.getCountryCurrencyDesc());
		assertNull(result.getExchangeRate());
		assertNull(result.getExchangePurchaseAmount());
	}
	
	@Test
	public void GetStorePurchaseNullIfNotFound() {
		val result = this.storePurchaseProcessor.getStorePurchase(2L, null);
		assertNull(result);
	}
	
	@Test
	public void CanCreateStorePurchase() {
		val now = LocalDateTime.now();
		val storePurchase = new StorePurchase();
		storePurchase.setDescription("Description1");
		storePurchase.setPurchaseAmount(new BigDecimal(10));
		storePurchase.setStorePurchaseId(1L);
		storePurchase.setTransactionDate(now);
		
		when(storePurchaseTransformer.convertDtoToEntity(any(StorePurchaseDto.class))).thenReturn(storePurchase);
		
		this.storePurchaseProcessor.createStorePurchase(new StorePurchaseDto());
		
		ArgumentCaptor<StorePurchase> savedCaptor = ArgumentCaptor.forClass(StorePurchase.class);
		verify(storePurchaseRepository).saveAndFlush(savedCaptor.capture());
		val savedValue = savedCaptor.getValue();
		assertEquals("Description1", savedValue.getDescription());
		assertTrue(savedValue.getPurchaseAmount().compareTo(new BigDecimal(10)) == 0);
		assertEquals(1L, savedValue.getStorePurchaseId());
		assertEquals(now, savedValue.getTransactionDate());
		
	}
}
