package br.com.edu.ifmt.sacode.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericRestController {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
}
// https://spring.io/guides/gs/spring-boot