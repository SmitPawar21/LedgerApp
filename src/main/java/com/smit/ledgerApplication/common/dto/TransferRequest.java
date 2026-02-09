package com.smit.ledgerApplication.common.dto;

import java.math.BigDecimal;
import java.util.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransferRequest {
	@NotNull(message = "Source ID is required")
	private UUID fromAccountId;
	
	@NotNull(message = "Target ID is required")
	private UUID toAccoundId;
	
	@Positive(message = "Amount must be greater than zero")
	private BigDecimal amount;
	
	@NotBlank(message = "Idempotency key is required")
	private String idempotencyKey;

	public TransferRequest(UUID fromAccountId, UUID toAccoundId, BigDecimal amount, String idempotencyKey) {
		super();
		this.fromAccountId = fromAccountId;
		this.toAccoundId = toAccoundId;
		this.amount = amount;
		this.idempotencyKey = idempotencyKey;
	}

	public UUID getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(UUID fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public UUID getToAccoundId() {
		return toAccoundId;
	}

	public void setToAccoundId(UUID toAccoundId) {
		this.toAccoundId = toAccoundId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getIdempotencyKey() {
		return idempotencyKey;
	}

	public void setIdempotencyKey(String idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}
	
}
