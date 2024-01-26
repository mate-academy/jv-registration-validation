package core.basesyntax.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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
