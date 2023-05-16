package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "normalLogin";
    private static final String DEFAULT_PASSWORD = "12345678";
    private static final int DEFAULT_AGE = 25;
    private static final String TEST_INVALID_LOGIN = "login";
    private static final String TEST_INVALID_PASSWORD = "123";
    private static final int TEST_INVALID_AGE = 10;

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin(TEST_INVALID_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validLogin_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_twoEqualsLogin_notOk() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setAge(DEFAULT_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_password_notOk() {
        user.setPassword(TEST_INVALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_notOk() {
        user.setAge(TEST_INVALID_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
