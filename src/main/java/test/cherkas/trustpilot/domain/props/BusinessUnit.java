package test.cherkas.trustpilot.domain.props;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessUnit {

    @JsonProperty(value = "numberOfReviews")
    private Integer reviewsCount;

    @JsonProperty(value = "trustScore")
    private Double rating;

    public BusinessUnit(Integer reviewsCount, Double rating) {
        this.reviewsCount = reviewsCount;
        this.rating = rating;
    }

    public BusinessUnit() {
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
