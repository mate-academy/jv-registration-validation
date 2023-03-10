package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "login";
    private static final String VALID_PASSWORD = "password";
    private static final String SHORT_PASSWORD = "pasik";
    private static final Integer VALID_AGE = 18;
    private static final Integer INVALID_AGE = 10;
    private static final String EMPTY_LOGIN = "";

    private static final User userIsNull = null;
    private static User testUser;

    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        testUser = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        testUser.setLogin(VALID_LOGIN);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
    }

    @Test
    void registerNullUserNotOK() {
        try {
            registrationService.register(userIsNull);
        } catch (RegistrationException e) {
            return;
        }
        fail("Object User must not be empty (null) ");
    }

    @Test
    void registerNullLoginNotOK() {
        testUser.setLogin(null);
        try {
            registrationService.register(testUser);
        } catch (RegistrationException e) {
            return;
        }
        fail("Login object must not be empty (null) ");
    }

    @Test
    void registerNullPasswordNotOK() {
        testUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void registerNullAgeNotOK() {
        testUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void registerInvalidAgeNotOK() {
        testUser.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void registerValidUserOk() {
        User expected = null;
        try {
            expected = registrationService.register(testUser);
        } catch (RegistrationException e) {
            fail("User is valid and must be added");
        }
        assertEquals(testUser,expected,"User is valid");
    }

    @Test
    void registerCloneUserNotOk() {
        try {
            registrationService.register(testUser);
        } catch (RegistrationException e) {
            fail("users are valid and must be added");
        }
        try {
            registrationService.register(testUser);
        } catch (RegistrationException e) {
            return;
        }
        fail("Two record whit same login -> exception ");
    }

    @Test
    void registerEmptyLoginNotOk() {
        testUser.setLogin(EMPTY_LOGIN);
        try {
            registrationService.register(testUser);
        } catch (RegistrationException e) {
            return;
        }
        fail("Login must have a value");
    }

    @Test
    void registerShortPasswordNotOk() {
        testUser.setPassword(SHORT_PASSWORD);
        try {
            registrationService.register(testUser);
        } catch (RegistrationException e) {
            return;
        }
        fail("User password is at least 6 characters, mast have");
    }
}
