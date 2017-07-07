package com.viseo.companion.controller;

import com.viseo.companion.domain.Event;
import com.viseo.companion.dto.ReviewDTO;
import com.viseo.companion.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "${endpoint.addReview}", method = POST)
    public ReviewDTO addReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.addReview(reviewDTO);
    }

    @RequestMapping(value = "${endpoint.getEventRating}", method = GET)
    public String[] getEventRating(@PathVariable("eventId") long eventId) {
        return reviewService.getEventRating(eventId);
    }

    @RequestMapping(value = "${endpoint.updateReview}", method = PUT)
    public ReviewDTO updateReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.updateReview(reviewDTO);
    }

   @RequestMapping(value = "${endpoint.getReviewedEvents}", method = GET)
    public List<Event> getEventsReviewed(@PathVariable("userId") long userId) {
        return reviewService.getEventsReviewed(userId);
    }


}
