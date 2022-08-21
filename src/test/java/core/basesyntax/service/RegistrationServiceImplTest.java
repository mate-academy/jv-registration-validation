package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_USER_LOGIN = "login123";
    private static final String INVALID_SHORTER_LOGIN_LENGTH = "log12";
    private static final String INVALID_LONGER_LOGIN_LENGTH = "login123456";
    private static final String VALID_USER_PASSWORD = "password123";
    private static final String VALID_OLD_USER_PASSWORD = "password12345";
    private static final String INVALID_SHORTER_PASSWORD_LENGTH = "pass1";
    private static final String INVALID_LONGER_PASSWORD_LENGTH = "password12345678";
    private static final Integer VALID_USER_AGE = 25;
    private static final Integer VALID_OLD_USER_AGE = 30;
    private static final Integer INVALID_LOWER_USER_AGE = 10;
    private static final Integer INVALID_HIGHER_USER_AGE = 110;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_USER_LOGIN);
        user.setPassword(VALID_USER_PASSWORD);
        user.setAge(VALID_USER_AGE);
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_NullUserLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserLoginGreaterThanZero_Ok() {
        assertTrue(user.getLogin().length() > 0);
    }

    @Test
    void register_ShorterUserLogin_NotOk() {
        user.setLogin(INVALID_SHORTER_LOGIN_LENGTH);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LongerUserLogin_NotOk() {
        user.setLogin(INVALID_LONGER_LOGIN_LENGTH);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullUserPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserPasswordGreaterThanZero_Ok() {
        assertTrue(user.getPassword().length() > 0);
    }

    @Test
    void register_ShorterUserPassword_NotOk() {
        user.setPassword(INVALID_SHORTER_PASSWORD_LENGTH);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_LongerUserPassword_NotOk() {
        user.setPassword(INVALID_LONGER_PASSWORD_LENGTH);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullUserAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserAgeGreaterThanZero_Ok() {
        assertTrue(user.getAge() > 0);
    }

    @Test
    void register_UserAgeLowerThanZero_NotOk() {
        assertFalse(user.getAge() < 0);
    }

    @Test
    void register_LowerUserAge_NotOk() {
        user.setAge(INVALID_LOWER_USER_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_HigherUserAge_NotOk() {
        user.setAge(INVALID_HIGHER_USER_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserWithTheSameLoginExist_NotOk() {
        User oldUser = new User();
        oldUser.setLogin(VALID_USER_LOGIN);
        oldUser.setPassword(VALID_OLD_USER_PASSWORD);
        oldUser.setAge(VALID_OLD_USER_AGE);
        registrationService.register(oldUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
