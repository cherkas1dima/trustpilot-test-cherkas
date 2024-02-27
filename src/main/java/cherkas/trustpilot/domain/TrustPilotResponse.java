package cherkas.trustpilot.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class TrustPilotResponse {

    private Integer reviewsCount;
    private Double rating;
}
