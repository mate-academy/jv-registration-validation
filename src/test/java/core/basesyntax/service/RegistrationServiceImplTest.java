package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final String VALID_LOGIN = "validlogin";
    private static final String VALID_PASSWORD = "validpassword";

    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_Ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_shortNameUser_NotOk() {
        User user = new User("short", VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        User user = new User(VALID_LOGIN, "short", VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, 16);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAgeUser_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, -1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(user);
        User newUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
