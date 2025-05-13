package com.wexinc.interviews.noble.storepurchase.client;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wexinc.interviews.noble.storepurchase.client.dto.ExchangeRateDto;
import com.wexinc.interviews.noble.storepurchase.client.dto.ExchangeRateResponse;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Profile("!test")
public class TreasuryExchangeClientImpl implements TreasuryExchangeClient {
	private final String URL = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange?fields=country_currency_desc,exchange_rate,record_date&filter=country_currency_desc:in:(%s),record_date:lte:%s&sort=-record_date";
	@Autowired
	RestTemplate restTemplate;

	@Override
	public List<ExchangeRateDto> getExchangeRates(String countryCurrencyDesc, LocalDateTime recordDate) {
		try {
			val url = String.format(URL, countryCurrencyDesc, recordDate.toLocalDate().toString());
			val response = restTemplate.getForEntity(url, ExchangeRateResponse.class);
			return response.getBody().getData();
		} catch (Exception e) {
			// consider handling in such a way that the service still returns the purchase indo, but with status 206 code partial content
			log.error("Error retrieving exchange rate for " + countryCurrencyDesc, e);
			throw e;
		}
		
	}

}
