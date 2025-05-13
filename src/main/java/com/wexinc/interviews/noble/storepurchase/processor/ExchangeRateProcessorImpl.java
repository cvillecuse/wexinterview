package com.wexinc.interviews.noble.storepurchase.processor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wexinc.interviews.noble.storepurchase.client.TreasuryExchangeClient;
import com.wexinc.interviews.noble.storepurchase.client.dto.ExchangeRateDto;

import lombok.val;

@Service
public class ExchangeRateProcessorImpl implements ExchangeRateProcessor {
	
	@Autowired
	TreasuryExchangeClient treasuryExchangeClient;

	@Override
	public double getExchangeRate(String countryCurrencyDesc, LocalDateTime transactionDate) {
		val exchangeRates = this.treasuryExchangeClient.getExchangeRates(countryCurrencyDesc, transactionDate);
		if(exchangeRates == null) {
			throw new ResponseStatusException(HttpStatus.PARTIAL_CONTENT, "Could Not Find Valid Exchange Rate");
		}
		List<ExchangeRateDto> last6Months = exchangeRates.stream().filter(x -> x.getRecordDate().isAfter(transactionDate.toLocalDate().minusMonths(6)))
				.sorted( (x, y) -> y.getRecordDate().compareTo(x.getRecordDate()) ).toList();
		if(last6Months.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could Not Find Valid Exchange Rate");
		}
		val exchangeRate = Double.parseDouble(last6Months.getFirst().getExchangeRate());
		return exchangeRate;
	}

}
