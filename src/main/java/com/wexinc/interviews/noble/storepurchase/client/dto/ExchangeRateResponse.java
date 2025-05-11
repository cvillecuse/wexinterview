package com.wexinc.interviews.noble.storepurchase.client.dto;

import java.util.List;

import lombok.Data;

@Data
public class ExchangeRateResponse {
	private List<ExchangeRateDto> data;
}
