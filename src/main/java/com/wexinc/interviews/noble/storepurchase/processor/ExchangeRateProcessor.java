package com.wexinc.interviews.noble.storepurchase.processor;

import java.time.LocalDateTime;



public interface ExchangeRateProcessor {
	double getExchangeRate(String countryCurrencyDesc, LocalDateTime transactionDate);
}
