package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storage;
    private static final String VALID_LOGIN_1 = "validLogin1";
    private static final String VALID_LOGIN_2 = "validLogin2";
    private static final String EMPTY_LOGIN = "";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String EMPTY_PASSWORD = "";
    private static final String INVALID_LENGTH = "short";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();

    }

    @Test
    void validRegistration_Ok() {
        User validUser = new User(VALID_LOGIN_1, VALID_PASSWORD, 20);
        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    void duplicateLogin_NotOk() {
        User user1 = new User(VALID_LOGIN_2, VALID_PASSWORD, 25);
        User user2 = new User(VALID_LOGIN_2, VALID_PASSWORD, 30);

        assertDoesNotThrow(() -> registrationService.register(user1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void invalidLoginLength_NotOk() {
        User invalidUser = new User(INVALID_LENGTH, VALID_PASSWORD, 25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

    @Test
    void invalidPasswordLength_NotOk() {
        User invalidUser = new User(VALID_LOGIN_2, INVALID_LENGTH, 25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

    @Test
    void invalidAge_NotOk() {
        User invalidUser = new User(VALID_LOGIN_2, VALID_PASSWORD, 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

    @Test
    void nullLogin_NotOk() {
        User invalidUser = new User(null, VALID_PASSWORD, 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

    @Test
    void nullPassword_NotOk() {
        User invalidUser = new User(VALID_LOGIN_2, null, 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

    @Test
    void nullAge_NotOk() {
        User invalidUser = new User(VALID_LOGIN_2, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

    @Test
    void negativeAge_NotOk() {
        User invalidUser = new User(VALID_LOGIN_2, VALID_PASSWORD, -1000);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

    @Test
    void emptyLogin_NotOk() {
        User invalidUser = new User(EMPTY_LOGIN, VALID_PASSWORD, 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

    @Test
    void emptyPassword_NotOk() {
        User invalidUser = new User(VALID_LOGIN_2, EMPTY_PASSWORD, 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidUser));
    }

}
