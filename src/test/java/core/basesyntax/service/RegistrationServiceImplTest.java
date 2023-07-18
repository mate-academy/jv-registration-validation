package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static final String CORRECT_LOGIN = "john1998";
    private static final String CORRECT_PASSWORD = "qwerty";
    private static final int CORRECT_AGE = 23;
    private static final User VALID1_USER = new User(CORRECT_LOGIN,
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User VALID2_USER = new User("kate2006",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User INVALID_LOGIN_USER = new User("max",
            CORRECT_PASSWORD, CORRECT_AGE);
    private static final User INVALID_PASSWORD_USER = new User(CORRECT_LOGIN,
            "ghabf", CORRECT_AGE);
    private static final User INVALID_AGE_USER = new User(CORRECT_LOGIN,
            CORRECT_PASSWORD, 16);
    private static final User NULL_USER = null;
    private static final User EXISTING_USER = new User(CORRECT_LOGIN,
            CORRECT_PASSWORD, CORRECT_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validSuccessfulRegistration_Ok() {
        User registeredUser = registrationService.register(VALID1_USER);
        assertNotNull(registeredUser);
        assertEquals(VALID1_USER, registeredUser);
    }

    @Test
    void register_notCorrectLogin_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_LOGIN_USER);
        });
    }

    @Test
    void register_notCorrectPassword_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_PASSWORD_USER);
        });
    }

    @Test
    void register_UnderageUser_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_AGE_USER);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_USER);
        });
    }

    @Test
    void register_alreadyExistingUser_NotOk() {
        registrationService.register(VALID2_USER);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(EXISTING_USER);
        });
    }
}
