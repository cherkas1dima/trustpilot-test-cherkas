package test.cherkas.trustpilot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import test.cherkas.trustpilot.domain.TrustPilotResponse;
import test.cherkas.trustpilot.exception.DomainValidationException;
import test.cherkas.trustpilot.exception.TrustPilotBadRequestException;
import test.cherkas.trustpilot.exception.TrustPilotServerErrorException;
import test.cherkas.trustpilot.mapper.TrustPilotResponseConverter;
import test.cherkas.trustpilot.utils.Parser;

@Service
public class TrustPilotServiceImpl implements TrustPilotService {

    private final WebClient trustpilotClient;
    private final Parser parser;
    private final TrustPilotResponseConverter converter;

    @Autowired
    public TrustPilotServiceImpl(WebClient trustpilotClient, Parser parser, TrustPilotResponseConverter converter) {
        this.trustpilotClient = trustpilotClient;
        this.parser = parser;
        this.converter = converter;
    }

    @Override
    @Cacheable("trustpilotData")
    public Mono<TrustPilotResponse> getTrustPilot(String domain) {
        if (!domainValidation(domain)) {
            throw new DomainValidationException("domain value is null or not valid");
        }
        return trustpilotClient.get()
                .uri(String.join("", "/", domain))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new TrustPilotBadRequestException(domain + " API not found")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        error -> Mono.error(new TrustPilotServerErrorException(domain + " Server is not responding")))
                .bodyToMono(String.class)
                .map(str ->  {
                    var businessUnit = parser.getBusinessUnitFromHtml(str);
                    return converter.convert(businessUnit);
                })
                .cache();
    }

    private boolean domainValidation(String domain) {
        return domain != null && domain.contains(".com");
    }
}
