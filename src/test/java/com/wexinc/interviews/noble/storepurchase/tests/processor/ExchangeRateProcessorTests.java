package com.wexinc.interviews.noble.storepurchase.tests.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.wexinc.interviews.noble.storepurchase.client.TreasuryExchangeClient;
import com.wexinc.interviews.noble.storepurchase.client.dto.ExchangeRateDto;
import com.wexinc.interviews.noble.storepurchase.processor.ExchangeRateProcessorImpl;

import lombok.val;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateProcessorTests {
	@Mock
	private TreasuryExchangeClient treasuryExchangeClient;
	
	@InjectMocks
	private ExchangeRateProcessorImpl exchangeRateProcessor;
	
	@Test
	public void CanGetExchangeRate() {
		val now = LocalDate.now();
		val exchangeRates = new ArrayList<ExchangeRateDto>();
		val exchangeRate1 = new ExchangeRateDto();
		exchangeRate1.setCountryCurrencyDescription("Canada-Dollar");
		exchangeRate1.setExchangeRate("1.4");
		exchangeRate1.setRecordDate(now);
		val exchangeRate2 = new ExchangeRateDto();
		exchangeRate2.setCountryCurrencyDescription("Canada-Dollar");
		exchangeRate2.setExchangeRate("1.5");
		exchangeRate2.setRecordDate(now.minusDays(1));
		exchangeRates.add(exchangeRate1);
		exchangeRates.add(exchangeRate2);
		when(treasuryExchangeClient.getExchangeRates(eq("Canada-Dollar"), any(LocalDateTime.class))).thenReturn(exchangeRates);
		val result = this.exchangeRateProcessor.getExchangeRate("Canada-Dollar", LocalDateTime.now());
		assertEquals(1.4, result);
	}
	
	@Test
	public void GetExchangeRateExceptionOnNotFound() {
		Exception expectedException = null;
		val now = LocalDate.now();
		val exchangeRates = new ArrayList<ExchangeRateDto>();
		val exchangeRate1 = new ExchangeRateDto();
		exchangeRate1.setCountryCurrencyDescription("Canada-Dollar");
		exchangeRate1.setExchangeRate("1.4");
		exchangeRate1.setRecordDate(now.minusYears(1));
		val exchangeRate2 = new ExchangeRateDto();
		exchangeRate2.setCountryCurrencyDescription("Canada-Dollar");
		exchangeRate2.setExchangeRate("1.5");
		exchangeRate2.setRecordDate(now.minusDays(1).minusYears(1));
		exchangeRates.add(exchangeRate1);
		exchangeRates.add(exchangeRate2);
		when(treasuryExchangeClient.getExchangeRates(eq("Canada-Dollar"), any(LocalDateTime.class))).thenReturn(exchangeRates);
		try {
			this.exchangeRateProcessor.getExchangeRate("Canada-Dollar", LocalDateTime.now());
		} catch (Exception e) {
			expectedException = e;
		}
		
		assertNotNull(expectedException);
		assertTrue(expectedException instanceof ResponseStatusException);
		assertEquals(HttpStatus.BAD_REQUEST, ((ResponseStatusException)expectedException).getStatusCode());
		assertEquals("400 BAD_REQUEST \"Could Not Find Valid Exchange Rate\"", expectedException.getMessage());
	}
}
