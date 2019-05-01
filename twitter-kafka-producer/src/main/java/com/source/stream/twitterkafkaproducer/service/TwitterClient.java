package com.source.stream.twitterkafkaproducer.service;

import com.google.gson.Gson;
import com.source.stream.twitterkafkaproducer.model.Tweet;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitterClient {

	@Autowired
	KafkaProducer producer;

	@Value("${spring.twitter.consumer-key}")
	String twitterConsumerKey;

	@Value("${spring.twitter.consumer-secret}")
	String twitterConsumerSecret;

	@Value("${spring.twitter.access-token}")
	String twitterAccessToken;

	@Value("${spring.twitter.token-secret}")
	String twitterTokenSecret;

	@Value("${spring.twitter.hashtag}")
	String hashtag;

	private Client client;
	private BlockingQueue<String> queue;
	private Gson gson;

	public TwitterClient() {

	}

	public void streamTweets() {
		// Configure auth
		Authentication authentication = new OAuth1(this.twitterConsumerKey, this.twitterConsumerSecret,
				this.twitterAccessToken, this.twitterTokenSecret);

		// track the hashtag
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		endpoint.trackTerms(Collections.singletonList(this.hashtag));

		queue = new LinkedBlockingQueue<>(10000);

		client = new ClientBuilder().hosts(Constants.STREAM_HOST).authentication(authentication).endpoint(endpoint)
				.processor(new StringDelimitedProcessor(queue)).build();
		gson = new Gson();
		client.connect();
		while (!client.isDone()) {
			try {
				Tweet tweet = gson.fromJson(queue.take(), Tweet.class);
				System.out.printf("Fetched tweet id %d\n", tweet.getId());
				producer.sendMessage(tweet);
			} catch (InterruptedException e) {
				System.out.println("Interrupted blocking read from queue.");
			}
		}
		client.stop();
		System.out.println("Closed connection.");
	}
}
