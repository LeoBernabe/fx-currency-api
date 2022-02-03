package com.example.demo.services;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.LatestRatesResponse;
import com.example.demo.models.CurrencyRate;

public interface CurrencyRateService {

	public ResponseEntity<CurrencyRate> convertCurrencyRate(String buyCurrency, float buyAmount, String sellCurrency, float sellAmount);

	public ResponseEntity<LatestRatesResponse> getAllCurrencies(String base);
}
