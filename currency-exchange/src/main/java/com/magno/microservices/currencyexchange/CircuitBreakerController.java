package com.magno.microservices.currencyexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
	// @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
	// @CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
	// @RateLimiter(name = "default")
	// 10s => 10000 calls to the sample api
	@Bulkhead(name = "default")
	public String sampleApi() {

		logger.info("########" + "Sample API call received");
//		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy",
//				String.class);
//		return forEntity.getBody();

		return "sample-api";
	}

	public String hardCodedResponse(Exception ex) {
		return "Fallback Response";
	}
}