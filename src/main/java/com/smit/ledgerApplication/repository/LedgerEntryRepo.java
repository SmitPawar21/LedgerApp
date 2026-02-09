package com.smit.ledgerApplication.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smit.ledgerApplication.model.LedgerEntry;

@Repository
public interface LedgerEntryRepo extends JpaRepository<LedgerEntry, UUID> {
	
	@Query("SELECT COALESCE(SUM(l.amount), 0) FROM LedgerEntry l where l.account.id = :id")
	BigDecimal calculateBalanceByAccountId(@Param("id") UUID id);
	
	boolean existsByIdempotencyKey(String idempotencyKey);
}
