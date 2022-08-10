package com.magno.microservices.currencyexchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment enviroment;

	@Autowired
	private CurrencyExchangeRepository currencyRepository;

	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange = currencyRepository.findByFromAndTo(from, to);
		if (currencyExchange == null) {
			throw new RuntimeException("Unable to Find data for " + from + " and to: " + to);
		}
		currencyExchange.setEnvironment(enviroment.getProperty("local.server.port"));

		return currencyExchange;
	}
}
