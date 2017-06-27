package com.viseo.companion.converter;

import com.viseo.companion.domain.Review;
import com.viseo.companion.dto.ReviewDTO;

public class NotationConverter {

    static private long NULL = -1;
    static private long NEW = 0;

    public ReviewDTO getDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setVersion(review.getVersion());

        if (review.getUser() == null) {
            dto.setUserId(NULL);
        } else {
            dto.setUserId(review.getUser().getId());
        }

        if (review.getEvent() == null) {
            dto.setEventId(NULL);
        } else {
            dto.setEventId(review.getEvent().getId());
        }
        return dto;
    }

    public void apply(ReviewDTO dto, Review review) {
        if (dto.getId() != NEW && review.getVersion() != dto.getVersion()) {
            throw new RuntimeException("Entity " + review + " was updated since DTO was built.");
        }
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

    }
}
