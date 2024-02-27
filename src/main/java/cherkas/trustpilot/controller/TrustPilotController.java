package cherkas.trustpilot.controller;

import cherkas.trustpilot.domain.TrustPilotResponse;
import cherkas.trustpilot.service.TrustPilotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TrustPilotController {

    private final TrustPilotService trustPilotService;

    @GetMapping("/reviews/{domain}")
    private ResponseEntity<Mono<TrustPilotResponse>> getTrustPilotData(@PathVariable("domain") String domain) {

        var resp = trustPilotService.getTrustPilot(domain);
        resp.subscribe(response -> log.info("successfully got response: {}", response));
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
