package com.source.stream.twitterkafkaproducer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.source.stream.twitterkafkaproducer.model.Tweet;

@Configuration
public class KafkaConfiguration {

	@Bean
	public ProducerFactory<String, Tweet> producerFactory() {
		Map<String, Object> config = new HashMap();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<String, Tweet>(config);
	}
	
	@Bean
	public KafkaTemplate<String, Tweet> kafkaTemplate() {
		return new KafkaTemplate<String, Tweet>(producerFactory());
	}
}
