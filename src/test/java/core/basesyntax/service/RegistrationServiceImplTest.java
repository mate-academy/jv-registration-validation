package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final long TEST_USER_ID = 999L;
    private static final String TEST_LOGIN = "Login";
    private static final String PASSWORD_VALID = "Password";
    private static final String PASSWORD_NOT_VALID = "Pas%";
    private static final int MIN_AGE = 18;
    private static final int LESS_AGE = 16;
    private static final int GREATER_AGE = 135;
    private static final int ZERO = 0;
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User(TEST_USER_ID, TEST_LOGIN, PASSWORD_VALID, MIN_AGE);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void registration_regularUser_isOk() {
        User actualUser = registrationService.register(testUser);
        assertEquals(testUser, actualUser);
    }

    @Test
    void registration_nullUser_isNotOk() {
        testUser = null;
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "User can't be null!");
    }

    @Test
    void registration_withNullPassword_isNotOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Password can't be null!");
    }

    @Test
    void registration_withInvalidPassword_isNotOk() {
        testUser.setPassword(PASSWORD_NOT_VALID);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Password must be longer than 5");
    }

    @Test
    void registration_withNullLogin_isNotOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Login can't be null!");
    }

    @Test
    void registration_withEmptyLogin_isNotOk() {
        testUser.setLogin("");
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Login can't be empty!");
    }

    @Test
    void registration_withSameLogin_isNotOk() {
        User actualUser = registrationService.register(testUser);
        assertEquals(actualUser.getLogin(), testUser.getLogin());
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Login is present, chose another");
    }

    @Test
    void registration_withNullAge_isNotOk() {
        testUser.setAge(null);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Age can't be null!");
    }

    @Test
    void registration_withLessThan18YearsOld_isNotOk() {
        testUser.setAge(LESS_AGE);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Age can't be less than 18");
    }

    @Test
    void registration_withGreaterAge_isNotOk() {
        testUser.setAge(GREATER_AGE);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Age can't be greater than 125");
    }

    @Test
    void registration_withNegativeAge_isNotOk() {
        testUser.setAge(ZERO);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(testUser), "Age can't be negative");
    }
}
