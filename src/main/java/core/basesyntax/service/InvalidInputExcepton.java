package core.basesyntax.service;

public class InvalidInputExcepton extends RuntimeException {
    public InvalidInputExcepton() {
        super();
    }

    public InvalidInputExcepton(String message) {
        super();
        System.out.println(message);
    }
}
