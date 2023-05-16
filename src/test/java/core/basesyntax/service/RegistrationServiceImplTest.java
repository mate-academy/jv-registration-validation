package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "jessy";
    private static final String VALID_PASSWORD = "123456";
    private static final String INVALID_PASSWORD = "12345";
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private final RegistrationService registrationService =
            new RegistrationServiceImpl();

    @BeforeEach
    void emptyStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullInput_notOk() {
        User input = null;
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User input = new User(null,
                VALID_PASSWORD,
                VALID_AGE);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User input = new User(VALID_LOGIN,
                null,
                VALID_AGE);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User input = new User(VALID_LOGIN,
                VALID_PASSWORD,
                null);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void register_userAlreadyExists_notOk() {
        User input = new User(VALID_LOGIN,
                VALID_PASSWORD,
                VALID_AGE);
        registrationService.register(input);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void register_userTooYoung_notOk() {
        User input = new User(VALID_LOGIN,
                VALID_PASSWORD,
                INVALID_AGE);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        User input = new User(VALID_LOGIN,
                INVALID_PASSWORD,
                VALID_AGE);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void register_userHasId_ok() {
        User input = new User(VALID_LOGIN,
                VALID_PASSWORD,
                VALID_AGE);
        User expected = registrationService.register(input);
        assertNotEquals(expected.getId(),
                null);
    }

    @Test
    void register_ok() {
        User input = new User(VALID_LOGIN,
                VALID_PASSWORD,
                VALID_AGE);
        registrationService.register(input);
        User expected = Storage.people.get(Storage.people.size() - 1);
        assertEquals(expected,
                input);
    }

    @Test
    void register_storageIncrease_ok() {
        User input = new User(VALID_LOGIN,
                VALID_PASSWORD,
                VALID_AGE);
        int oldSize = Storage.people.size();
        registrationService.register(input);
        assertEquals(oldSize + 1,
                Storage.people.size());
    }
}
