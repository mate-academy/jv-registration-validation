package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final User VALID_USER = new User("username", "qwerty1", 20);
    private static final User NEW_USER = new User("newUser", "qwerty1", 20);
    private static final User INVALID_USER = new User("in_us", "qty1", 10);
    private static final User NULL_USER = null;
    private static final User UNDERAGE_USER = new User("underageUser", "qwerty1", 15);
    private static final User INVALID_LOGIN_USER = new User("1n", "qwerty1", 20);
    private static final User INVALID_PASSWORD_USER = new User("invalidPasswordUser", "in_p", 20);
    private static final User NULL_LOGIN_USER = new User(null, "qwerty1", 15);
    private static final User NULL_PASSWORD_USER = new User("nullPassword", null, 20);
    private static final User NULL_AGE_USER = new User("nullAge", "qwety1", null);
    private static StorageDaoImpl storage;
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void userDataValidRegistration() {
        registrationService.register(VALID_USER);
        User userFromStorage = storage.get(VALID_USER.getLogin());
        assertEquals(VALID_USER, userFromStorage, "User data is invalid!");
    }

    @Test
    void userDataInvalidRegistration_notOK() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_USER);
        });
    }

    @Test
    void registrationExistingUser_notOk() {
        assertNotNull(storage.get(VALID_USER.getLogin()));
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(VALID_USER);
        });
    }

    @Test
    void userUnderage_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(UNDERAGE_USER);
        });
    }

    @Test
    void userInvalidLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_LOGIN_USER);
        });
    }

    @Test
    void userInvalidPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_PASSWORD_USER);
        });
    }

    @Test
    void registrationNotExistingUser() {
        assertNull(storage.get(NEW_USER.getLogin()));
        assertDoesNotThrow(() -> registrationService.register(NEW_USER));
    }

    @Test
    void userNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_USER);
        });
    }

    @Test
    void userAgeNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_AGE_USER);
        });
    }

    @Test
    void userLoginNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_LOGIN_USER);
        });
    }

    @Test
    void userPasswordNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_PASSWORD_USER);
        });
    }
}
