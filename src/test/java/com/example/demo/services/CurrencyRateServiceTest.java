package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.LatestRatesResponse;
import com.example.demo.models.CurrencyRate;

@SpringBootTest
public class CurrencyRateServiceTest {
	
	@Autowired
	private CurrencyRateService currencyRateService;
	
	@Test
	public void whenBuyAmountHasValue_thenCurrencyRateShouldHaveResult() {
	    String buyCurrency = "EUR";
	    float buyAmount = 10;
	    String sellCurrency = "USD";
	    CurrencyRate currencyRate = currencyRateService.convertCurrencyRate(buyCurrency,buyAmount,sellCurrency,0).getBody();

		float rateOffered = buyAmount * currencyRate.getConversionRate();
	    assertThat(currencyRate.getSellAmount()).isEqualTo(rateOffered);
	    assertThat(currencyRate).isNotNull();
	 }
	
	@Test
	public void whenBuyAmountHasNoValue_thenCurrencyRateShouldHaveResult() {
	    String buyCurrency = "EUR";
	    float buyAmount = 0;
	    String sellCurrency = "USD";
	    float sellAmount = 10;
	    CurrencyRate currencyRate = currencyRateService.convertCurrencyRate(buyCurrency,buyAmount,sellCurrency,sellAmount).getBody();

		float rateOffered = sellAmount / currencyRate.getConversionRate();
	    assertThat(currencyRate.getBuyAmount()).isEqualTo(rateOffered);
	    assertThat(currencyRate).isNotNull();
	 }
	
	@Test
	public void whenBuyAmountHasInvalidValue_thenCurrencyRateShouldNotHaveResult() {
	    String buyCurrency = "USD";
	    float buyAmount = 0;
	    String sellCurrency = "EUR";
	    float sellAmount = 10;
	    CurrencyRate currencyRate = currencyRateService.convertCurrencyRate(buyCurrency,buyAmount,sellCurrency,sellAmount).getBody();

	    assertThat(currencyRate).isEqualTo(new CurrencyRate());
	 }
	
	@Test
	public void whenBaseIsValid_thenGetAllCurrenciesShouldHaveResult() {
	    String base = "EUR";
	    LatestRatesResponse latestRatesResponse = currencyRateService.getAllCurrencies(base).getBody();

	    assertThat(latestRatesResponse).isNotNull();
	 }
	
	@Test
	public void whenBaseIsNotValid_thenGetAllCurrenciesShouldNotHaveResult() {
	    String base = "USD";

	    LatestRatesResponse latestRatesResponse = currencyRateService.getAllCurrencies(base).getBody();

	    assertThat(latestRatesResponse).isEqualTo(new LatestRatesResponse());
	 }
}
