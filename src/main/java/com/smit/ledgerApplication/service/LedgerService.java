package com.smit.ledgerApplication.service;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.smit.ledgerApplication.common.dto.TransferRequest;
import com.smit.ledgerApplication.common.exception.InsufficientFundsException;
import com.smit.ledgerApplication.model.Account;
import com.smit.ledgerApplication.model.LedgerEntry;
import com.smit.ledgerApplication.repository.AccountRepository;
import com.smit.ledgerApplication.repository.LedgerEntryRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LedgerService {
	
	private final AccountRepository accountRepo;
	private final LedgerEntryRepo ledgerEntryRepo;
	
	public LedgerService(AccountRepository accountRepo, LedgerEntryRepo ledgerEntryRepo) {
		this.accountRepo = accountRepo;
		this.ledgerEntryRepo = ledgerEntryRepo;
	}
	
	@Retryable(value = ObjectOptimisticLockingFailureException.class)
	@Transactional
	public void transfer(TransferRequest transferRequest) {	
		if (ledgerEntryRepo.existsByIdempotencyKey(transferRequest.getIdempotencyKey())) {
	        return; // Or throw a custom DuplicateTransactionException
	    }
		
		Account source = accountRepo.findByIdWithLock(transferRequest.getFromAccountId())
				.orElseThrow(() -> new IllegalArgumentException("Source Account Not Found"));
		
		Account target = accountRepo.findByIdWithLock(transferRequest.getToAccoundId())
				.orElseThrow(() -> new IllegalArgumentException("Target Account Not Found"));
		
		BigDecimal sourceBalance = ledgerEntryRepo.calculateBalanceByAccountId(transferRequest.getFromAccountId());
		
		if(sourceBalance.compareTo(transferRequest.getAmount()) < 0) {
			throw new InsufficientFundsException(
	                String.format("Insufficient funds. Available: %.2f, Requested: %.2f", 
	                    sourceBalance, transferRequest.getAmount())
	            );
		}
		
		LedgerEntry debit = new LedgerEntry();
		debit.setAccount(source);
		debit.setAmount(transferRequest.getAmount().negate());
		debit.setDescription("Transfer To Account "+ transferRequest.getToAccoundId());
		debit.setCreatedAt(OffsetDateTime.now());
		
		LedgerEntry credit = new LedgerEntry();
		credit.setAccount(target);
		credit.setAmount(transferRequest.getAmount());
		credit.setDescription("Transfer From Account "+ transferRequest.getFromAccountId());
		credit.setCreatedAt(OffsetDateTime.now());
		
		source.setLastUpdatedAt(OffsetDateTime.now());
	    accountRepo.save(source);
		
		ledgerEntryRepo.save(debit);
		ledgerEntryRepo.save(credit);
	}
}
