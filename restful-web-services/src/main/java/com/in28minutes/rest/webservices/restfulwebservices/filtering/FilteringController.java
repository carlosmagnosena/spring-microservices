package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue retrieveSomeBean() {
		SomeBean someBean = new SomeBean("value 1", "value 2", "value 3");
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mapping.setFilters(filters);

		return mapping;
	}

	@GetMapping("/filtering-list")
	public List<SomeBean> retrieveListOfSomeBeans() {
		return Arrays.asList(new SomeBean("value 1", "value 2", "value 3"),
				new SomeBean("value 12", "value 22", "value 33"));
	}
}
