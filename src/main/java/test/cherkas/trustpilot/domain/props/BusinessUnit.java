package test.cherkas.trustpilot.domain.props;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessUnit {

    private Integer numberOfReviews;
    private Double trustScore;
}
