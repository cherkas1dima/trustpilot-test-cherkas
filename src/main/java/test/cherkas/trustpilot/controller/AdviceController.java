package test.cherkas.trustpilot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import test.cherkas.trustpilot.exception.DomainValidationException;
import test.cherkas.trustpilot.exception.ParserException;
import test.cherkas.trustpilot.exception.TrustPilotBadRequestException;
import test.cherkas.trustpilot.exception.TrustPilotServerErrorException;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(value = {TrustPilotBadRequestException.class})
    public ResponseEntity<String> apiNotFoundException(TrustPilotBadRequestException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TrustPilotServerErrorException.class})
    public ResponseEntity<String> notRespondingServerException(TrustPilotServerErrorException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = {ParserException.class})
    public ResponseEntity<String> parserException(ParserException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {DomainValidationException.class})
    public ResponseEntity<String> validationException(ParserException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
