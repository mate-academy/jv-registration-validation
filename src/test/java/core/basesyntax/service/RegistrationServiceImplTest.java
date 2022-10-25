package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_VALID_LOGIN = "Academy";
    private static final String DEFAULT_VALID_PASSWORD = "Mate22";
    private static final String SHORT_VALID_PASSWORD = "Mate";
    private static final String LONG_VALID_PASSWORD = "MateAcademy2022";
    private static final int DEFAULT_VALID_AGE = 18;
    private static final int ABOVE_VALID_AGE = 27;
    private static final int BELOW_VALID_AGE = 17;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_VALID_AGE);
        user.setLogin(DEFAULT_VALID_LOGIN);
        user.setPassword(DEFAULT_VALID_PASSWORD);
    }

    @Test
    void registerUserIsNull_NotOk() {
        user = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserPasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserAgeIsNull_NotOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserAgeBelowLimit_NotOk() {
        user.setAge(BELOW_VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserAgeIsAboveLimit_Ok() {
        user.setAge(ABOVE_VALID_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerUserPasswordShort_NotOk() {
        user.setPassword(SHORT_VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserPasswordLong_Ok() {
        user.setPassword(LONG_VALID_PASSWORD);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerUserValidPassword_Ok() {
        user.setPassword(DEFAULT_VALID_PASSWORD);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerUserIsAdult_Ok() {
        user.setAge(DEFAULT_VALID_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerUserWithSameLogin_NotOk() {
        User sameLogin = new User();
        user.setPassword(DEFAULT_VALID_PASSWORD);
        registrationService.register(user);
        sameLogin.setPassword(LONG_VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLogin));

    }
}
