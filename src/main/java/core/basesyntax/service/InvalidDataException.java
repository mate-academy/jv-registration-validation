package core.basesyntax.service;

class InvalidDataException extends RuntimeException {
    InvalidDataException(final String message) {
        super(message);
    }
}
