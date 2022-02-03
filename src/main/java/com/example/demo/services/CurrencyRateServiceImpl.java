package com.example.demo.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.dto.LatestRatesResponse;
import com.example.demo.models.CurrencyRate;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

	private static final Logger log = Logger.getLogger(String.valueOf(CurrencyRateServiceImpl.class));
	
	@Autowired
	RestTemplate restTemplate;
	

	@Value("${exchange-rate.api-key}")
	private String exchangeRateApiKey;	
	
	@Value("${exchange-rate.url}")
	private String exchangeRateUrl;
	
    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
    
    
	@Override
	public ResponseEntity<CurrencyRate> convertCurrencyRate(String buyCurrency, float buyAmount, String sellCurrency,
			float sellAmount) {

		CurrencyRate currencyRate = new CurrencyRate();
		
		if(!buyCurrency.equalsIgnoreCase("EUR")) {
		    log.info("Other buy currency is not allowed except EUR due to free subscription.");
			return new ResponseEntity<CurrencyRate>(currencyRate, HttpStatus.BAD_REQUEST);
		}
		
		LatestRatesResponse latestRatesResponse = getCurrencyLatestRate(buyCurrency, sellCurrency);
		float conversionRate = latestRatesResponse.getRates().get(sellCurrency);

		// convert buy amount to sell amount when it is not equal to 0
		if(buyAmount != 0) {
			float rateOffered = buyAmount * conversionRate;
			currencyRate.setBuyAmount(buyAmount);
			currencyRate.setSellAmount(rateOffered);
		} else {
			float rateOffered = sellAmount / conversionRate;
			currencyRate.setBuyAmount(rateOffered);
			currencyRate.setSellAmount(sellAmount);
		}
		currencyRate.setBuyCurrency(buyCurrency);
		currencyRate.setSellCurrency(sellCurrency);
		currencyRate.setConversionRate(conversionRate);

		return new ResponseEntity<CurrencyRate>(currencyRate, HttpStatus.OK);
	}
	
	private LatestRatesResponse getCurrencyLatestRate(String base, String symbols) {
		try {	
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("access_key", exchangeRateApiKey);
			
			if(!base.isBlank())
				params.add("base", base);
			if(!symbols.isBlank())
				params.add("symbols", symbols);

			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(exchangeRateUrl).queryParams(params);
			
			ResponseEntity<LatestRatesResponse> resultResponseEntity = 
					restTemplate.exchange(builder.toUriString(), HttpMethod.GET, getHttpEntity(), LatestRatesResponse.class);	
			
			return resultResponseEntity.getBody();
		}catch (final HttpClientErrorException e) {

		    log.info(e.getStatusCode().toString() + " " + e.getResponseBodyAsString());
		    
			return new LatestRatesResponse();
		}
	}


	@Override
	public ResponseEntity<LatestRatesResponse> getAllCurrencies(String base) {
		
		LatestRatesResponse latestRatesResponse = getCurrencyLatestRate(base, "");
		
		return new ResponseEntity<LatestRatesResponse>(latestRatesResponse, HttpStatus.OK);
	}

}
