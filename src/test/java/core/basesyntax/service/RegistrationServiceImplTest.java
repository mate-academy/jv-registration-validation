package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storage;
    private static final String VALID_LOGIN_1 = "validLogin1";
    private static final String VALID_LOGIN_2 = "validLogin2";

    private static final String VALID_PASSWORD = "validPassword";
    private static final String INVALID_LENGTH = "short";
    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();

    }

    @Test
    void validRegistration() {
        User validUser = new User(VALID_LOGIN_1, VALID_PASSWORD, 20);
        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    void duplicateLogin() {
        User user1 = new User(VALID_LOGIN_2, VALID_PASSWORD, 25);
        User user2 = new User(VALID_LOGIN_2, VALID_PASSWORD, 30);

        assertDoesNotThrow(() -> registrationService.register(user1));
        assertThrows(AuthenticationException.class, () -> registrationService.register(user2));
    }

    @Test
    void invalidLoginLength() {
        User invalidUser = new User(INVALID_LENGTH, VALID_PASSWORD, 25);

        assertThrows(AuthenticationException.class, () -> registrationService.register(invalidUser));
    }

    @Test
    void invalidPasswordLength() {
        User invalidUser = new User(VALID_LOGIN_2, INVALID_LENGTH, 25);

        assertThrows(AuthenticationException.class, () -> registrationService.register(invalidUser));
    }

    @Test
    void invalidAge() {
        User invalidUser = new User(VALID_LOGIN_2, VALID_PASSWORD, 17);

        assertThrows(AuthenticationException.class, () -> registrationService.register(invalidUser));
    }
}