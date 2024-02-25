package test.cherkas.trustpilot.service;

import reactor.core.publisher.Mono;
import test.cherkas.trustpilot.domain.TrustPilotResponse;

public interface TrustPilotService {

    Mono<TrustPilotResponse> getTrustPilot(String domain);
}
