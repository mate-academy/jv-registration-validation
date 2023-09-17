package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "marshmallow";
    private static final Integer DEFAULT_AGE = 19;
    private static final String DEFAULT_PASSWORD = "morning1";
    private static final String UNACCEPTABLE_LOGIN = "bobby";
    private static final Integer UNACCEPTABLE_AGE = 17;
    private static final String UNACCEPTABLE_PASSWORD = "morn";
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_RegisterSameUserTwice_NotOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        Storage.PEOPLE.add(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UnacceptableAge_NotOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, UNACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UnacceptablePassword_NotOk() {
        User user = new User(DEFAULT_LOGIN, UNACCEPTABLE_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullValuePassword_NotOk() {
        User user = new User(DEFAULT_LOGIN, null, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullValueLogin_NotOk() {
        User user = new User(null, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UnacceptableLengthLogin_NotOk() {
        User user = new User(UNACCEPTABLE_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserValidated_Ok() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }
}
