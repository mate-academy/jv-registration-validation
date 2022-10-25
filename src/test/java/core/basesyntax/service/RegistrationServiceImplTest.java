package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final int ADULT_AGE = 18;
    private static final int BELOW_ADULT_AGE = 17;
    private static final int ABOVE_ADULT_AGE = 19;
    private static final String DEFAULT_VALID_LOGIN = "UserLogin";
    private static final String DEFAULT_VALID_PASSWORD = "UserPassword";
    private static final String SHORT_PASSWORD = "Short";
    private User user;

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

    @Test
    void register_userIsNullThrowsException_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsNullThrowsException_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsNullThrowsException_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsNullThrowsException_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userShortPasswordThrowsException_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserValidPassword_ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user), "Storage should contains registered user");
    }

    @Test
    void register_userAgeBelowThresholdThrowsException_notOk() {
        user.setAge(BELOW_ADULT_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeAboveThreshold_ok() {
        user.setAge(ABOVE_ADULT_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user), "Storage should contains registered user");
    }

    @Test
    void register_userExactAdultAge_ok() {
        user.setAge(ADULT_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertTrue(Storage.people.contains(user), "Storage should contains registered user");
    }

    @Test
    void register_sameUserThrowsException_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_differentUserWithSameLoginThrowsException_notOk() {
        User sameLoginUser = new User();
        user.setAge(ABOVE_ADULT_AGE);
        Storage.people.add(user);
        sameLoginUser.setAge(ADULT_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLoginUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
