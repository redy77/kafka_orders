package ru.victor.kafka_orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class KafkaOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaOrdersApplication.class, args);
	}

}
