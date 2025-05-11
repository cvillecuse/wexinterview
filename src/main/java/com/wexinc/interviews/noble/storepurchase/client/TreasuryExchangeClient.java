package com.wexinc.interviews.noble.storepurchase.client;

import java.time.LocalDateTime;
import java.util.List;

import com.wexinc.interviews.noble.storepurchase.client.dto.ExchangeRateDto;

public interface TreasuryExchangeClient {
	List<ExchangeRateDto> getExchangeRates(String countryCurrencyDesc, LocalDateTime recordDate);
}
