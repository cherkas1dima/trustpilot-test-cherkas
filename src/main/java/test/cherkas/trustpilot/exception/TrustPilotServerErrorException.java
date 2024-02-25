package test.cherkas.trustpilot.exception;

public class TrustPilotServerErrorException extends RuntimeException {

    public TrustPilotServerErrorException(String message) {
        super(message);
    }
}
