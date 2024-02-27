package cherkas.trustpilot.controller;

import cherkas.trustpilot.domain.TrustPilotResponse;
import cherkas.trustpilot.service.TrustPilotService;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TrustPilotController.class)
@Import(TrustPilotService.class)
public class TrustPilotControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    WebClient webClient;

    @MockBean
    Cache<String, TrustPilotResponse> caffeineCache;

    @Test
    void getTrustPilotDataFromCacheTest() throws Exception {
        Mockito
                .when(caffeineCache.getIfPresent(any()))
                .thenReturn(TrustPilotResponse.builder().reviewsCount(1460).rating(4.9).build());

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

    @Test
    void getTrustPilotDataNegativeTest() throws Exception {

        webTestClient.get()
                .uri("/reviews/gullwingmotor.com")
                .exchange()
                .expectStatus().is5xxServerError()
                .returnResult(TrustPilotResponse.class)
                .getResponseBody()
                .blockFirst();
    }

    @Test
    void getTrustPilotDataValidationFailedTest() throws Exception {

        webTestClient.get()
                .uri("/reviews/gullwingmotor")
                .exchange()
                .expectStatus().is4xxClientError()
                .returnResult(TrustPilotResponse.class)
                .getResponseBody()
                .blockFirst();
    }
}
