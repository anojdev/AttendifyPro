package edu.miu.attendifypro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AttendifyProApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendifyProApplication.class, args);
	}

}
