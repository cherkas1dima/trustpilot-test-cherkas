package cherkas.trustpilot.controller;

import cherkas.trustpilot.domain.TrustPilotResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.OK;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "webclient.trustpilot.url=http://localhost:${wiremock.server.port}"
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrustPilotControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    WireMockServer wireMockServer;

    @Test
    @SneakyThrows
    void getTrustPilotDataPositiveTestWithCache() {

        wireMockServer.stubFor(get(urlPathEqualTo("/gullwingmotor.com"))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withBody(new ClassPathResource("test_html.txt").getContentAsString(UTF_8))));

        int expectedCount = 1460;
        double expectedRating = 4.9;

        //get data from api
        var response = webTestClient.get()
                .uri("/reviews/gullwingmotor.com")
                .exchange()
                .expectStatus().isOk()
                .returnResult(TrustPilotResponse.class)
                .getResponseBody()
                .blockFirst();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedCount, response.getReviewsCount());
        Assertions.assertEquals(expectedRating, response.getRating());

        //get data from cache
        var cachedResp = webTestClient.get()
                .uri("/reviews/gullwingmotor.com")
                .exchange()
                .expectStatus().isOk()
                .returnResult(TrustPilotResponse.class)
                .getResponseBody()
                .blockFirst();

        Assertions.assertNotNull(cachedResp);
        verify(exactly(1), getRequestedFor(urlEqualTo("/gullwingmotor.com")));
        Assertions.assertEquals(expectedCount, cachedResp.getReviewsCount());
        Assertions.assertEquals(expectedRating, cachedResp.getRating());
    }
}
