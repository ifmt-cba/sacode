package br.com.edu.ifmt.sacode;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "br.com.edu.ifmt.sacode")
@SpringBootApplication
public class SacodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SacodeApplication.class, args);
	}

}
