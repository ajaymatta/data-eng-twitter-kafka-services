package com.source.stream.twitterkafkaproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.source.stream.twitterkafkaproducer.model.Tweet;

@Service
public class KafkaProducer {

    private static final String TOPIC = "tweets";

    @Autowired
    private KafkaTemplate<String, Tweet> kafkaTemplate;

    public void sendMessage(Tweet message) {
        this.kafkaTemplate.send(TOPIC, message);
    }
    
}
