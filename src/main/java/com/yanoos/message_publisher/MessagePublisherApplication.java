package com.yanoos.message_publisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MessagePublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagePublisherApplication.class, args);
	}

}
