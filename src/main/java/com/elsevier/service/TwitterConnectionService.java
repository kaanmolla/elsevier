package com.elsevier.service;

import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


@Service
class TwitterConnectionService {

    private static final String TWITTER_CONSUMER_KEY = "xxx";
    private static final String TWITTER_CONSUMER_SECRET = "xxx";
    private static final String TWITTER_ACCESS_TOKEN = "xx-xx";
    private static final String TWITTER_ACCESS_TOKEN_SECRET = "xxx";

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
