package com.elsevier.service;

import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


@Service
class TwitterConnectionService {

    private static final String TWITTER_CONSUMER_KEY = "O4tsJ0sGpa3iR3d2qqs97uTUA";
    private static final String TWITTER_CONSUMER_SECRET = "Cu7z1ImJqXWre5SKRSu6oVs0BWXWytms6NhbjsbePPWziTD4VX";
    private static final String TWITTER_ACCESS_TOKEN = "307322697-riv6TwXIbdBPw71SBnUrvn7AFmATIdeJx8jtvi1s";
    private static final String TWITTER_ACCESS_TOKEN_SECRET = "AlgHsE2rSqybZ3znpXuvgtyJN4awVE5CnRArFPsolPC1o";

    private final Twitter twitterInstance;

    private TwitterConnectionService() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
        cb.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
        cb.setOAuthAccessToken(TWITTER_ACCESS_TOKEN);
        cb.setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());

        this.twitterInstance = tf.getInstance();
    }

    Twitter getTwitterInstance() {
        return this.twitterInstance;
    }

}
