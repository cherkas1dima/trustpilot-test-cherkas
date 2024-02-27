package cherkas.trustpilot.configuration;

import cherkas.trustpilot.domain.TrustPilotResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CaffeineCacheConfig {

    @Bean
    public Cache<String, TrustPilotResponse> caffeineCache(
            @Value("${caffeine.max_size}") Integer maxSize, @Value("${caffeine.duration}") Integer duration) {
        return Caffeine.newBuilder().maximumSize(maxSize)
                .expireAfterWrite(Duration.ofSeconds(duration)).build();
    }
}
