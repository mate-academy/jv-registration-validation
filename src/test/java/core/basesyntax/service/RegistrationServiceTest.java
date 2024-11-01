package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static User user;
    private static String DEFAULT_LOGIN = "login";
    private static String DEFAULT_PASSWORD = "password";
    private static Integer DEFAULT_AGE = 18;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void init() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_savingWithSameLogin_NotOk() {
        user.setLogin("nice_login");
        registrationService.register(user);
        User user2 = new User();
        user2.setLogin("nice_login");
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOK() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLess6Characters_NotOk() {
        user.setLogin("login");
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordShouldHaveAtLeastSixCharacters() {
        user.setPassword("11111");
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeShouldBeAtLeast18Years() {
        user.setAge(17);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }
}
