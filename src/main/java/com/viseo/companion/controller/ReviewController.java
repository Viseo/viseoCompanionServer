package com.viseo.companion.controller;

import com.viseo.companion.dto.ReviewDTO;
import com.viseo.companion.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getEventRating(@PathVariable("eventId") long eventId) {
        return reviewService.getEventRating(eventId);
    }

    @RequestMapping(value = "${endpoint.updateReview}", method = PUT)
    public ReviewDTO updateReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.updateReview(reviewDTO);
    }
}
