package cherkas.trustpilot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class TrustPilotWebConfig {

    private static final Integer BYTE_COUNT = 16 * 1024 * 1024;

    @Bean
    public WebClient trustpilotClient(@Value("${trustpilot.url}") String url) {
        return WebClient.builder()
                .baseUrl(url)
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(BYTE_COUNT))
                .build();
    }
}
