package com.wexinc.interviews.noble.storepurchase.featuretests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.wexinc.interviews.noble.storepurchase.client.TreasuryExchangeClient;
import com.wexinc.interviews.noble.storepurchase.client.dto.ExchangeRateDto;

import lombok.val;

@Service
@Profile("test")
public class TreasuryExchangeClientMock implements TreasuryExchangeClient {

	@Override
	public List<ExchangeRateDto> getExchangeRates(String countryCurrencyDesc, LocalDateTime recordDate) {
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
		return exchangeRates;
	}

}
