package com.wexinc.interviews.noble.storepurchase.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StorePurchaseDto {
	
	private Long storePurchaseId;
	
	@NotBlank(message = "Description is mandatory")
	@Size(max = 50)
	private String description;
	
	@DateTimeFormat( pattern="yyyy-MM-ddThh:mm:ss")
	@NotNull(message = "Transaction date is mandatory")
	@Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01]) (\\d{2}):(\\d{2}):(\\d{2})$", message = "Transaction Date must be yyyy-MM-dd hh:mm:ss")
	private String transactionDate;
	
	@NotNull(message = "Purchase Amount is mandatory")
	private BigDecimal purchaseAmount;
	
	private String countryCurrencyDesc;
	
	private Double exchangeRate;
	
	private BigDecimal exchangePurchaseAmount;
	
	
}
