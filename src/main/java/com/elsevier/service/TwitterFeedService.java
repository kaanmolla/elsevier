package com.elsevier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.Logger;

import java.util.ArrayList;
import java.util.List;


@Service
public class TwitterFeedService {

    private static final String ELSEVIER = "ElsevierNews";
    private static final Integer PAGE_SIZE = 200;

    private static final Logger LOG = Logger.getLogger(TwitterFeedService.class);

    @Autowired
    private TwitterConnectionService twitterConnectionService;

    public Status getUserStatus() {
        Twitter twitter = twitterConnectionService.getTwitterInstance();
        try {
            return twitter.showUser(ELSEVIER).getStatus();
        } catch (TwitterException e) {
            LOG.error("Error occured while getting user status: ", e);
        }
        return null;
    }

    public List<Status> collectTweets() {
        Twitter twitter = this.twitterConnectionService.getTwitterInstance();

        int page = 1;
        int index = 0;
        Paging paging = new Paging();
        paging.setCount(PAGE_SIZE);

        List<Status> tweets = new ArrayList<>();

        try {
            ResponseList<Status> result = getStatusesWithPagination(twitter, page, index, paging, tweets);
            while (result.size() == PAGE_SIZE) {
                page += 1;
                index += PAGE_SIZE;
                result = getStatusesWithPagination(twitter, page, index, paging, tweets);
            }
        } catch (TwitterException e) {
            LOG.error("Error occured while collecting tweets: ", e);
        }

        return tweets;
    }

    private ResponseList<Status> getStatusesWithPagination(Twitter twitter, int page, int index, Paging paging, List<Status> tweets) throws TwitterException {
        paging.setPage(page);
        ResponseList<Status> result = twitter.getUserTimeline(ELSEVIER, paging);
        tweets.addAll(index, result.subList(0, result.size()));
        return result;
    }

}
