package com.smit.ledgerApplication.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name="accounts")
public class Account {
	
	@Id
	@GeneratedValue
	@UuidGenerator
	@Column(name="account_id", updatable=false, nullable=false)
	private UUID id;
	
	@Column(name="owner_name", nullable=false, length=255)
	private String ownerName;
	
	@Version
	@Column(name="version", nullable=false)
	private Long version;
	
	@CreationTimestamp
	@Column(name="created_at", updatable=false)
	private OffsetDateTime createdAt;
	
	public Account() {}

	public Account(UUID id, String ownerName, Long version, OffsetDateTime createdAt) {
		super();
		this.id = id;
		this.ownerName = ownerName;
		this.version = version;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
