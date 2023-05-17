package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "normal";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final int DEFAULT_AGE = 18;
    private static final String TEST_INVALID_LOGIN = "";
    private static final String TEST_INVALID_PASSWORD = "123";
    private static final int TEST_INVALID_AGE = 10;
    private static final int TEST_NEGATIVE_AGE = -5;

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
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
    void register_twoEqualsLogin_notOk() {
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
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

    @Test
    void register_negativeAge_notOk() {
        user.setAge(TEST_NEGATIVE_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
