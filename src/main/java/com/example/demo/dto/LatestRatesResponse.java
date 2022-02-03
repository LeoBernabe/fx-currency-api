package com.example.demo.dto;

import java.util.Date;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter @Setter
public class LatestRatesResponse {
	private String success;
	private String base;
	private Date date;
	private Map<String, Float> rates;
}
