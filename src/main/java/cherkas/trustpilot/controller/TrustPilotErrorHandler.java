package cherkas.trustpilot.controller;

import cherkas.trustpilot.domain.error.TrustPilotErrorResponse;
import cherkas.trustpilot.exception.DomainValidationException;
import cherkas.trustpilot.exception.ParserException;
import cherkas.trustpilot.exception.TrustPilotBadRequestException;
import cherkas.trustpilot.exception.TrustPilotServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class TrustPilotErrorHandler {

    @ExceptionHandler(value = {TrustPilotBadRequestException.class})
    public ResponseEntity<TrustPilotErrorResponse> apiNotFoundException(TrustPilotBadRequestException ex) {
        var err = buildError(ex.getMessage(), "TrustPilotBadRequestException", "www.trustpilot.com have no review data for your domain", ex);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TrustPilotServerErrorException.class})
    public ResponseEntity<TrustPilotErrorResponse> notRespondingServerException(TrustPilotServerErrorException ex) {
        var err = buildError(ex.getMessage(), "TrustPilotServerErrorException", "Internal Server Error at www.trustpilot.com side", ex);
        log.error("Exception TrustPilotServerErrorException handled by ControllerAdvice");
        return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = {ParserException.class})
    public ResponseEntity<TrustPilotErrorResponse> parserException(ParserException ex) {
        var err = buildError(ex.getMessage(), "ParserException", "There are exception during response data parsing step", ex);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DomainValidationException.class})
    public ResponseEntity<TrustPilotErrorResponse> validationException(DomainValidationException ex) {
        var err = buildError(ex.getMessage(), "DomainValidationException", "Please check your domain", ex);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    private TrustPilotErrorResponse buildError(String message, String errorName, String description, RuntimeException ex) {
        log.error("Exception {} handled by ControllerAdvice: {}", errorName, ex.getStackTrace());
        return TrustPilotErrorResponse.builder()
                .errorName(errorName)
                .message(message)
                .description(description)
                .build();
    }
}
