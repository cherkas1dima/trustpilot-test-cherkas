package test.cherkas.trustpilot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import test.cherkas.trustpilot.domain.TrustPilotResponse;
import test.cherkas.trustpilot.service.TrustPilotService;

@RestController
public class TrustPilotController {

    private final TrustPilotService service;

    @Autowired
    public TrustPilotController(TrustPilotService service) {
        this.service = service;
    }

    @GetMapping("/reviews/{domain}")
    private ResponseEntity<Mono<TrustPilotResponse>> getEmployeeDetails(@PathVariable("domain") String domain) {

        var resp = service.getTrustPilot(domain);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
