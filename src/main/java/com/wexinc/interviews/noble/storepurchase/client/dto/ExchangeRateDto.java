package com.wexinc.interviews.noble.storepurchase.client.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExchangeRateDto {
	@JsonProperty("country_currency_desc")
	private String countryCurrencyDescription;
	
	@JsonProperty("exchange_rate")
	private String exchangeRate;
	
	@JsonProperty("record_date")
	private LocalDate recordDate;
}
