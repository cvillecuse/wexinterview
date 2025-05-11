package com.wexinc.interviews.noble.storepurchase.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "store_purchases")
public class StorePurchase {
	@Id
	@Column(name = "store_purchase_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long storePurchaseId;
	
	@Column(name = "description", length = 50)
	private String description;
	
	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;
	
	@Column(name = "purchase_amount")
	private BigDecimal purchaseAmount;
	
	
}
