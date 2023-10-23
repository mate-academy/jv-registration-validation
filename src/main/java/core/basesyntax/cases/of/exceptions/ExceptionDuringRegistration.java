package core.basesyntax.cases.of.exceptions;

public class ExceptionDuringRegistration extends RuntimeException {
    public ExceptionDuringRegistration(String massage) {
        super(massage);
    }
}
