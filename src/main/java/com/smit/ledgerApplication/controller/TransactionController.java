package com.smit.ledgerApplication.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smit.ledgerApplication.common.dto.TransferRequest;
import com.smit.ledgerApplication.service.LedgerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    
    private final LedgerService ledgerService;
    
    public TransactionController(LedgerService ledgerService) {
		super();
		this.ledgerService = ledgerService;
	}

	@PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> handleTransfer(@Valid @RequestBody TransferRequest transferRequest) {
        ledgerService.transfer(transferRequest);

        return ResponseEntity.ok(Map.of(
            "message", "Transfer processed successfully",
            "idempotencyKey", transferRequest.getIdempotencyKey()
        ));
    }
}
