package test.cherkas.trustpilot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.cherkas.trustpilot.domain.error.TrustPilotErrorResponse;
import test.cherkas.trustpilot.exception.DomainValidationException;
import test.cherkas.trustpilot.exception.ParserException;
import test.cherkas.trustpilot.exception.TrustPilotBadRequestException;
import test.cherkas.trustpilot.exception.TrustPilotServerErrorException;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(value = {TrustPilotBadRequestException.class})
    public ResponseEntity<TrustPilotErrorResponse> apiNotFoundException(TrustPilotBadRequestException ex) {
        var err = TrustPilotErrorResponse.builder()
                .errorName("TrustPilotBadRequestException")
                .message(ex.getMessage())
                .description("www.trustpilot.com have no review data for your domain")
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TrustPilotServerErrorException.class})
    public ResponseEntity<TrustPilotErrorResponse> notRespondingServerException(TrustPilotServerErrorException ex) {
        var err = TrustPilotErrorResponse.builder()
                .errorName("TrustPilotServerErrorException")
                .message(ex.getMessage())
                .description("Internal Server Error at www.trustpilot.com side")
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = {ParserException.class})
    public ResponseEntity<TrustPilotErrorResponse> parserException(ParserException ex) {
        var err = TrustPilotErrorResponse.builder()
                .errorName("ParserException")
                .message(ex.getMessage())
                .description("There are exception during response data parsing step")
                .build();
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DomainValidationException.class})
    public ResponseEntity<TrustPilotErrorResponse> validationException(DomainValidationException ex) {
        var err = TrustPilotErrorResponse.builder()
                .errorName("DomainValidationException")
                .message(ex.getMessage())
                .description("Please check your domain")
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}