package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User corectUser = new User(1211222121L, "notShort", "notShort", 19);
    private final User wrongLogin = new User(1211222121L, "Short", "notShort", 19);
    private final User wrongPassword = new User(1211222121L, "notShort", "Short", 19);
    private final User wrongAge = new User(1211222121L, "notShort", "notShort", 17);
    private final User emptyId = new User(null, "notShort", "notShort", 19);
    private final User emptyLogin = new User(1211222121L, null, "notShort", 19);
    private final User emptyPassword = new User(1211222121L, "notShort", null, 19);
    private final User emptyAge = new User(1211222121L, "notShort", "notShort", null);

    @Test
    void register_corectUser_ok() {
        assertEquals(registrationService.register(corectUser), corectUser);
    }

    @Test
    void register_wrongLogin_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(wrongLogin);
        });
    }

    @Test
    void register_wrongPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(wrongPassword);
        });
    }

    @Test
    void register_wrongAge_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(wrongAge);
        });
    }

    @Test
    void register_emptyId_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(emptyId);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(emptyLogin);
        });
    }

    @Test
    void register_emptyAge_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(emptyAge);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(emptyPassword);
        });
    }
}
