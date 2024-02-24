package test.cherkas.trustpilot.domain;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrustPilotResponse {

    private Integer reviewsCount;
    private Double rating;
}
