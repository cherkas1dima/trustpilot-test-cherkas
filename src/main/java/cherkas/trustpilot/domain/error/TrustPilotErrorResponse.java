package cherkas.trustpilot.domain.error;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class TrustPilotErrorResponse {

    private String description;
    private String message;
    private String errorName;
}
