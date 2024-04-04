package com.info.CalendarAssistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.info.CalendarAssistant")
public class CalendarAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarAssistantApplication.class, args);
	}

}
