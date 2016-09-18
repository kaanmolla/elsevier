package com.elsevier.controller;

import com.elsevier.service.TwitterFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import twitter4j.Status;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TwitterFeedController {

    @Autowired
    private TwitterFeedService twitterFeedService;

    private static final Integer PAGE_SIZE = 20;

    @RequestMapping("/")
    public String index() {
        return "redirect:/search/page/1";
    }

    @RequestMapping("/search/page/{pageNumber}")
    public String searchTweets(HttpServletRequest request, @PathVariable Integer pageNumber, Model model) {
        PagedListHolder<?> pagedTweets = (PagedListHolder<?>) request.getSession().getAttribute("tweets");

        if (pagedTweets == null) {
            pagedTweets = buildPagedListHolder(request);
        } else {
            Status userStatus = (Status) request.getSession().getAttribute("userStatus");
            if (!twitterFeedService.getUserStatus().equals(userStatus)) {
                pagedTweets = buildPagedListHolder(request);
            }
            final int toPage = pageNumber - 1;
            if (toPage <= pagedTweets.getPageCount() && toPage >= 0) {
                pagedTweets.setPage(toPage);
            }
        }

        request.getSession().setAttribute("tweets", pagedTweets);

        setPaginationParameters(model, pagedTweets);
        model.addAttribute("baseUrl", "/search/page/");
        model.addAttribute("pagedTweets", pagedTweets.getPageList());

        return "searchResult";
    }

    private void setPaginationParameters(Model model, PagedListHolder<?> pagedTweets) {
        int currentIndex = pagedTweets.getPage() + 1;
        int beginIndex = Math.max(1, currentIndex - PAGE_SIZE);
        int endIndex = Math.min(beginIndex + PAGE_SIZE, pagedTweets.getPageCount());
        int totalPageCount = pagedTweets.getPageCount();

        model.addAttribute("beginIndex", beginIndex);
        model.addAttribute("endIndex", endIndex);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("currentIndex", currentIndex);
    }

    private PagedListHolder<?> buildPagedListHolder(HttpServletRequest request) {
        PagedListHolder<?> pagedTweets;
        pagedTweets = new PagedListHolder<>(twitterFeedService.collectTweets());
        pagedTweets.setPageSize(PAGE_SIZE);
        request.getSession().setAttribute("userStatus", twitterFeedService.getUserStatus());
        return pagedTweets;
    }

}
