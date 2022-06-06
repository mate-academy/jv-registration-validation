package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN_1 = "Alice";
    private static final String VALID_LOGIN_2 = "Bob";
    private static final String VALID_LOGIN_3 = "SAM";
    private static final String NULL = null;
    private static final String EMPTY_STRING = "";

    private static final String VALID_PASSWORD_1 = "123456";
    private static final String INVALID_PASSWORD_1 = "12345";

    private static final Integer VALID_AGE = 18;
    private static final Integer INVALID_AGE = 17;

    private static final User VALID_USER_1 =
            new User(VALID_LOGIN_1, VALID_PASSWORD_1, VALID_AGE);
    private static final User SAME_VALID_USER_1 =
            new User(VALID_LOGIN_1, VALID_PASSWORD_1, VALID_AGE);

    private static final User INVALID_PASSWORD_NULL =
            new User(VALID_LOGIN_2, NULL, VALID_AGE);
    private static final User INVALID_PASSWORD_EMPTY =
            new User(VALID_LOGIN_3, EMPTY_STRING, VALID_AGE);
    private static final User INVALID_PASSWORD_LESS_6 =
            new User(VALID_LOGIN_1, INVALID_PASSWORD_1, VALID_AGE);

    private static final User INVALID_LOGIN_USER_1 =
            new User(NULL, VALID_PASSWORD_1, VALID_AGE);
    private static final User INVALID_AGE_USER_1 =
            new User(VALID_LOGIN_1, VALID_PASSWORD_1, INVALID_AGE);
    private static final User INVALID_NULL_USER = null;

    private RegistrationService regService;

    @BeforeEach
    void setUp() {
        regService = new RegistrationServiceImpl();
    }

    @Test
    void register_passwordMore6Char_ok() {
        User actual = regService.register(VALID_USER_1);
        assertEquals(VALID_USER_1, actual);
    }

    @Test
    void register_passwordLess6Char_notOk() {
        assertThrows(RuntimeException.class, () -> {
            regService.register(INVALID_PASSWORD_LESS_6);
        });
    }

    @Test
    void register_addTheSameUser_notOk() {
        regService.register(VALID_USER_1);
        assertThrows(RuntimeException.class, () -> {
            regService.register(SAME_VALID_USER_1);
        });
    }

    @Test
    void register_passwordNullOrEmpty_notOk() {
        assertThrows(RuntimeException.class, () -> {
            regService.register(INVALID_PASSWORD_NULL);
        });
        assertThrows(RuntimeException.class, () -> {
            regService.register(INVALID_PASSWORD_EMPTY);
        });
    }

    @Test
    void register_userNullOrLoginNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            regService.register(INVALID_NULL_USER);
        });
        assertThrows(RuntimeException.class, () -> {
            regService.register(INVALID_LOGIN_USER_1);
        });
    }

    @Test
    void register_less18_notOk() {
        assertThrows(RuntimeException.class, () -> {
            regService.register(INVALID_AGE_USER_1);
        });
    }
}
