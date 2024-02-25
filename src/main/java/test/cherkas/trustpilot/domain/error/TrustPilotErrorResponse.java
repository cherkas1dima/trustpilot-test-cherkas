package test.cherkas.trustpilot.domain.error;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrustPilotErrorResponse {

    private String description;
    private String message;
    private String errorName;
}
