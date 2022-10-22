package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final int ADULT_AGE = 18;
    private static final int BELOW_ADULT_AGE = 17;
    private static final int ABOVE_ADULT_AGE = 19;
    private static final String DEFAULT_VALID_LOGIN = "UserLogin";
    private static final String DEFAULT_VALID_PASSWORD = "UserPassword";
    private static final String SHORT_PASSWORD = "Short";
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(ABOVE_ADULT_AGE);
        user.setPassword(DEFAULT_VALID_PASSWORD);
        user.setLogin(DEFAULT_VALID_LOGIN);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerUserIsNullThrowsException_OK() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserLoginIsNullThrowsException_OK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserPasswordIsNullThrowsException_OK() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserAgeIsNullThrowsException_OK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserShortPasswordThrowsException_OK() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserValidPassword_OK() {
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUserAgeBelowThresholdThrowsException_OK() {
        user.setAge(BELOW_ADULT_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserAgeAboveThreshold_OK() {
        user.setAge(ABOVE_ADULT_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerUserExactAdultAge_OK() {
        user.setAge(ADULT_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void registerSameUserThrowsException_OK() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerDifferentUserWithSameLoginThrowsException_OK() {
        User sameLoginUser = new User();
        user.setAge(ABOVE_ADULT_AGE);
        registrationService.register(user);
        sameLoginUser.setAge(ADULT_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLoginUser));
    }
}