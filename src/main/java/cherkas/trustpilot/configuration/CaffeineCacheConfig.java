package cherkas.trustpilot.configuration;

import cherkas.trustpilot.domain.TrustPilotResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CaffeineCacheConfig {

    private static final Integer MAXIMUM_SIZE = 1000;
    private static final Integer DURATION = 60;

    @Bean
    public Cache<String, TrustPilotResponse> caffeineCache() {
        return Caffeine.newBuilder().maximumSize(MAXIMUM_SIZE)
                .expireAfterWrite(Duration.ofSeconds(DURATION)).build();
    }
}
