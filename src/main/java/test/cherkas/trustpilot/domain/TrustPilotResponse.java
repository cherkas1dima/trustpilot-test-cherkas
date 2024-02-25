package test.cherkas.trustpilot.domain;

import lombok.*;

@Builder
@ToString
public class TrustPilotResponse {

    private Integer reviewsCount;
    private Double rating;

    public TrustPilotResponse(Integer reviewsCount, Double rating) {
        this.reviewsCount = reviewsCount;
        this.rating = rating;
    }

    public TrustPilotResponse() {
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
