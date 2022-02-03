package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LatestRatesResponse;
import com.example.demo.models.CurrencyRate;
import com.example.demo.services.CurrencyRateService;

@RestController
public class CurrencyRateController {
	
		@Autowired
		private CurrencyRateService currencyRateService;
		
		@GetMapping("/get-all-latest-rate")
		public ResponseEntity<LatestRatesResponse> getAllCurrencies(
				@RequestParam(name = "base", required = false, defaultValue = "EUR") String base){
			return currencyRateService.getAllCurrencies(base);

        }
		
		@GetMapping("/get-currency-rate-result")
		public ResponseEntity<CurrencyRate> convertCurrencyRate(
				@RequestParam(name = "buyCurrency", required = false, defaultValue = "EUR") String buyCurrency,
				@RequestParam(name = "buyAmount", required = false, defaultValue = "0") float buyAmount,
				@RequestParam(name = "sellCurrency", required = false, defaultValue = "USD") String sellCurrency,
				@RequestParam(name = "sellAmount", required = false, defaultValue = "0") float sellAmount){
			return currencyRateService.convertCurrencyRate(buyCurrency, buyAmount, sellCurrency, sellAmount);
        }
}
