package test.cherkas.trustpilot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import test.cherkas.trustpilot.domain.TrustPilotResponse;
import test.cherkas.trustpilot.mapper.TrustPilotResponseConverter;
import test.cherkas.trustpilot.utils.Parser;

@Service
public class TrustPilotServiceImpl implements TrustPilotService {

    private static final Logger logger = LoggerFactory.getLogger(TrustPilotServiceImpl.class);

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
        return trustpilotClient.get()
                .uri(String.join("", "/", domain))
                .retrieve()
                .bodyToMono(String.class)
                .map(str ->  {
                    var businessUnit = parser.getBusinessUnitFromHtml(str);
                    return converter.convert(businessUnit);
                })
                .cache();
    }
}
