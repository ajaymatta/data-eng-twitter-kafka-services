package com.source.stream.twitterkafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.source.stream.twitterkafkaproducer.service.TwitterClient;

@SpringBootApplication
public class TwitterKafkaProducerApplication implements ApplicationRunner {

	@Autowired
	TwitterClient client;

	public static void main(String[] args) {
		SpringApplication.run(TwitterKafkaProducerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		client.streamTweets();
	}
}
