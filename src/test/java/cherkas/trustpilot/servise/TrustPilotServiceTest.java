package cherkas.trustpilot.servise;

import cherkas.trustpilot.configuration.TrustPilotServiceTestConfig;
import cherkas.trustpilot.exception.DomainValidationException;
import cherkas.trustpilot.service.TrustPilotService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(TrustPilotServiceTestConfig.class)
public class TrustPilotServiceTest {

    @Autowired
    TrustPilotService service;

    @Test
    void trustPilotServicePositiveTest() {
        var resp = service.getTrustPilot("gullwingmotor.com");
        Assertions.assertNotNull(resp);
        resp.subscribe(response -> {
            Assertions.assertNotNull(response.getReviewsCount());
            Assertions.assertNotNull(response.getRating());
        });
    }

    @Test
    void validationFailedTest() {
        Assertions.assertThrows(DomainValidationException.class, () -> service.getTrustPilot("error"));
    }
}
