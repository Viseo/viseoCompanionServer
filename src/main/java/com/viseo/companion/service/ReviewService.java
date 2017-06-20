package com.viseo.companion.service;

import com.viseo.companion.converter.NotationConverter;
import com.viseo.companion.dao.EventDAO;
import com.viseo.companion.dao.ReviewDAO;
import com.viseo.companion.dao.UserDAO;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Review;
import com.viseo.companion.domain.User;
import com.viseo.companion.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EventDAO eventDAO;

    private NotationConverter converter = new NotationConverter();

    public ReviewDTO addReview(ReviewDTO reviewDto) {
        try {
            Review review = toReview(reviewDto);
            if (review != null) {
                review = reviewDAO.addReview(review);
                return converter.getDTO(review);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;

    }

    private Review toReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        User user = userDAO.getUser(reviewDTO.getUserId());
        Event event = eventDAO.getEvent(reviewDTO.getEventId());
        if (user != null && event != null) {
            review.setUser(user);
            review.setEvent(event);
            converter.apply(reviewDTO, review);
            return review;
        }
        return null;
    }

    public String getEventRating(long eventId) {
        return reviewDAO.getEventRating(eventId);
    }

    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        try {
            Review review = reviewDAO.getReview(reviewDTO.getId());
            if (review != null) {
                converter.apply(reviewDTO, review);
                return converter.getDTO(reviewDAO.updateReview(review));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
