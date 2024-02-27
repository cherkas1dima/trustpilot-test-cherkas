package cherkas.trustpilot.mapper;

import cherkas.trustpilot.domain.TrustPilotResponse;
import cherkas.trustpilot.domain.props.BusinessUnit;
import org.springframework.stereotype.Component;

public class TrustPilotResponseMapper {

    public static TrustPilotResponse fromBusinessUnitToResponse(BusinessUnit unit) {
        return TrustPilotResponse.builder()
                .reviewsCount(unit.getNumberOfReviews())
                .rating(unit.getTrustScore())
                .build();
    }
}
