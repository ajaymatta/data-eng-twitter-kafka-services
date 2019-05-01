package com.source.stream.twitterkafkaproducer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.source.stream.twitterkafkaproducer.model.Tweet;
import com.source.stream.twitterkafkaproducer.service.KafkaProducer;

@RestController
@RequestMapping
public class KafkaController {

    private final KafkaProducer producer;

    @Autowired
    KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestBody Tweet tweet) {
        this.producer.sendMessage(tweet);
    }
}
