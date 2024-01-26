package core.basesyntax.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationExceptionTest {
    @Test
    void throwAndCheckExceptionWithoutCaption() {
        assertThrows(RegistrationException.class, () -> {
            throw new RegistrationException();
        });
    }

    @Test
    void throwAndCheckExceptionWithCaption() {
        assertThrows(RegistrationException.class, () -> {
            throw new RegistrationException("This is sample exception which we are throwing and checking");
        });
    }
}