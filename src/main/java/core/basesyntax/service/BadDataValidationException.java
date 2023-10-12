package core.basesyntax.service;

import java.io.IOException;

public class BadDataValidationException extends RuntimeException {
    public BadDataValidationException(String message) {
        super(message);
    }
}
