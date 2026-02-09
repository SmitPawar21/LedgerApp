package com.smit.ledgerApplication.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
@Table(
	name = "ledger_entries",
	indexes = {
		@Index(name="idx_ledger_account_id", columnList="account_id")
	}
)
public class LedgerEntry {
	
	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name="id", updatable=false, nullable=false)
	private UUID id;
	
	@Column(name="transaction_id", nullable=false)
	private UUID transactionId;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="account_id", nullable=false)
	private Account account;
	
	@Column(name="amount", precision=19, scale=4, nullable=false)
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status", nullable=false)
	private TransactionStatus status = TransactionStatus.PENDING;
	
	@Column(name="idempotency_key", nullable=false, unique=true, length=255)
	private String idempotencyKey;
	
	@Column(name="description")
	private String description;
	
	@CreationTimestamp
	@Column(name="created_at", updatable=false)
	private OffsetDateTime createdAt;
	
	public LedgerEntry() {}

	public LedgerEntry(UUID id, UUID transactionId, Account account, BigDecimal amount, TransactionStatus status,
			String idempotencyKey, String description, OffsetDateTime createdAt) {
		super();
		this.id = id;
		this.transactionId = transactionId;
		this.account = account;
		this.amount = amount;
		this.status = status;
		this.idempotencyKey = idempotencyKey;
		this.description = description;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public String getIdempotencyKey() {
		return idempotencyKey;
	}

	public void setIdempotencyKey(String idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
