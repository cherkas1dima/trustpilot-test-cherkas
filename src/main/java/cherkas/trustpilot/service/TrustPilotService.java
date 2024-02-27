package cherkas.trustpilot.service;

import cherkas.trustpilot.domain.TrustPilotResponse;
import cherkas.trustpilot.domain.props.BusinessUnit;
import cherkas.trustpilot.exception.DomainValidationException;
import cherkas.trustpilot.exception.TrustPilotBadRequestException;
import cherkas.trustpilot.exception.TrustPilotServerErrorException;
import cherkas.trustpilot.mapper.TrustPilotResponseMapper;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static cherkas.trustpilot.utils.Parser.getBusinessUnitFromHtml;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrustPilotService {

    private static final String DOMAIN = ".com";

    private final WebClient trustPilotClient;
    private final TrustPilotResponseMapper trustPilotResponseMapper;
    private final Cache<String, TrustPilotResponse> caffeineCache;

    public Mono<TrustPilotResponse> getTrustPilot(String domain) {

        validationCheck(domain);
        TrustPilotResponse cachedVersion = this.caffeineCache.getIfPresent(domain);

        return cachedVersion != null
                ? Mono.just(cachedVersion)
                : trustPilotClient.get()
                .uri(String.join("", "/", domain))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new TrustPilotBadRequestException("API not found for domain: " + domain)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        error -> Mono.error(new TrustPilotServerErrorException("Server is not responding for domain: " + domain)))
                .bodyToMono(String.class)
                .map(htmlResp -> processHtmlToResponse(htmlResp, domain))
                .doOnNext(response -> this.caffeineCache.put(domain, response));
    }

    private TrustPilotResponse processHtmlToResponse(String htmlResp, String domain) {
        log.info("successfully got html response for {} domain", domain);
        BusinessUnit businessUnit = getBusinessUnitFromHtml(htmlResp);
        log.info("businessUnit info parsed successfully: {}", businessUnit);
        return trustPilotResponseMapper.fromBusinessUnitToResponse(businessUnit);
    }

    private void validationCheck(String domain) {
        if (domain == null || !domain.contains(DOMAIN)) {
            throw new DomainValidationException("domain value (" + domain + ") is not valid");
        }
    }
}
