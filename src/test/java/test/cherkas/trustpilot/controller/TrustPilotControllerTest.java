package test.cherkas.trustpilot.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import test.cherkas.trustpilot.domain.TrustPilotResponse;
import test.cherkas.trustpilot.service.TrustPilotService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(TrustPilotController.class)
class TrustPilotControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    TrustPilotService service;

    @Test
    void getTrustPilotDataTest() throws Exception {

        when(service.getTrustPilot(anyString()))
                .thenReturn((Mono.just(TrustPilotResponse.builder().reviewsCount(1460).rating(4.9).build())));

        var response = webTestClient.get()
                .uri("/reviews/gullwingmotor.com")
                .exchange()
                .expectStatus().isOk()
                .returnResult(TrustPilotResponse.class)
                .getResponseBody()
                .blockFirst();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1460, response.getReviewsCount());
        Assertions.assertEquals(4.9, response.getRating());
    }
}
