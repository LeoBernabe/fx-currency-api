package com.example.demo.models;

import lombok.Data;

@Data
public class CurrencyRate {
	String buyCurrency; 
	float buyAmount; 
	String sellCurrency;
	float sellAmount;
	float conversionRate;
}
