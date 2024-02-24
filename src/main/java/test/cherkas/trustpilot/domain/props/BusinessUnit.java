package test.cherkas.trustpilot.domain.props;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessUnit {

    @JsonProperty(value = "numberOfReviews")
    private Integer reviewsCount;

    @JsonProperty(value = "trustScore")
    private Double rating;
}
