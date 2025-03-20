package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RegistrationExceptionTest {
    @Test
    void constructor_RuntimeException_OK() {
        assertThrows(RuntimeException.class, () -> {
            throw new RegistrationException("message");
        }, "RegistrationException must be inherited from RuntimeException.");
    }
}
