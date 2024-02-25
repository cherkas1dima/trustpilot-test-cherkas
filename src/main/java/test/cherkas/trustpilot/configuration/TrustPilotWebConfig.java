package test.cherkas.trustpilot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TrustPilotWebConfig {

    @Value("${trustpilot.url}")
    private String url;

    @Bean
    public WebClient trustpilotClient() {
        return WebClient.builder()
                .baseUrl(url)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();
    }
}
