package cherkas.trustpilot.configuration;

import cherkas.trustpilot.domain.TrustPilotResponse;
import cherkas.trustpilot.service.TrustPilotService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@TestConfiguration
public class TrustPilotServiceTestConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("https://www.trustpilot.com/review").codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();
    }

    @Bean
    public Cache<String, TrustPilotResponse> caffeineCache() {
        return Caffeine.newBuilder().maximumSize(100).expireAfterWrite(Duration.ofSeconds(60)).build();
    }

    @Bean
    public TrustPilotService service(WebClient webClient, Cache<String, TrustPilotResponse> caffeineCache) {
        return new TrustPilotService(webClient, caffeineCache);
    }
}
