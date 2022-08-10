package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;

	// GET
	// URI - /hello-world
	// method - " hello World"
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		String c = "casa";
		Boolean[] cs = new Boolean[128];
		for (int i = 0; i < c.length(); i++) {
			int val = c.charAt(i);
			if (cs[val]) {
				return "false";
			}
			cs[val] = true;
		}
		return "Hello World";
	}

	// hello-world-bean
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}

	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}

	@GetMapping(path = "/hello-world-inter")
	public String helloWorldInternationalized() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}

}
