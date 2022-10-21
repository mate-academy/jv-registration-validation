package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.UserExistsException;
import core.basesyntax.service.exception.UserValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String DEFAULT_LOGIN = "DEFAULT_LOGIN";
    private static final String DUPLICATE_LOGIN = "DUPLICATE_LOGIN";
    private static final int MIN_VALID_AGE = 18;
    private static final int VALID_AGE = 60;
    private static final int MAX_INVALID_AGE = 17;
    private static final String VALID_PASSWORD = "123456";
    private static final String INVALID_PASSWORD = "12345";
    private User defaultUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        defaultUser = new User();
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(VALID_PASSWORD);
        defaultUser.setAge(MIN_VALID_AGE);
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        User expectedUser = defaultUser;
        int expectedSize = Storage.people.size() + 1;
        User actualUser = registrationService.register(defaultUser);
        assertEquals(expectedUser, actualUser, "Test failed!"
                + "Expected return value " + expectedUser
                + ", but was " + actualUser);
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, Storage.people.size(), "Test failed!"
                + " Size of storage after adding valid user should be "
                + expectedSize + ", but was "
                + actualSize
        );
        assertTrue(Storage.people.contains(expectedUser),
                "Test failed! Expected storage people contains "
                        + expectedUser + " = true, but was false");
    }

    @Test
    public void register_loginIsNull_notOk() {
        defaultUser.setLogin(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        defaultUser.setLogin(DUPLICATE_LOGIN);
        registrationService.register(defaultUser);
        assertThrows(UserExistsException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_passwordIsNull_notOk() {
        defaultUser.setPassword(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_passwordLessThan6CharsLong_notOk() {
        defaultUser.setPassword(INVALID_PASSWORD);
        assertThrows(UserValidationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_ageLessThan18_notOk() {
        defaultUser.setAge(MAX_INVALID_AGE);
        assertThrows(UserValidationException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_ageIsGreaterThan18_ok() {
        defaultUser.setAge(VALID_AGE);
        registrationService.register(defaultUser);
        assertTrue(Storage.people.contains(defaultUser),
                "Test failed! Expected storage people contains "
                        + defaultUser + " = true, but was false");
    }
}
