package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.exception.UserInvalidException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final long ID = 1;
    private static final String LOGIN = "someLogin";
    private static final String PASSWORD = "password";
    private static final String INVALID_PASSWORD = "pas";
    private static final int AGE = 20;
    private static final int INVALID_AGE = 12;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(ID);
        user.setAge(AGE);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
    }

    @Test
    void register_user_isOk() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_userNull_isNotOk() {
        User userNull = null;
        Assertions.assertThrows(UserInvalidException.class, () -> {
            registrationService.register(userNull);
        }, "Expected " + UserInvalidException.class.getName() + ", but it wasn`t");
    }

    @Test
    void register_userWithNullPassword_isNotOk() {
        user.setPassword(null);
        Assertions.assertThrows(UserInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected " + UserInvalidException.class.getName() + ", but it wasn`t");
    }

    @Test
    void register_userWithNullLogin_isNotOk() {
        user.setLogin(null);
        Assertions.assertThrows(UserInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected " + UserInvalidException.class.getName() + ", but it wasn`t");
    }

    @Test
    void register_userWithInvalidPassword_isNotOk() {
        user.setPassword(INVALID_PASSWORD);
        Assertions.assertThrows(UserInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected " + UserInvalidException.class.getName() + ", but it wasn`t");
    }

    @Test
    void register_userWithSameLogin_isNotOk() {
        Assertions.assertThrows(UserInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected " + UserInvalidException.class + ", but it wasn`t ");
    }

    @Test
    void register_userWithNullLoginAndPassword_isNotOk() {
        user.setPassword(null);
        user.setLogin(null);
        Assertions.assertThrows(UserInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected " + UserInvalidException.class + ", but it wasn`t");
    }

    @Test
    void register_userInvalidAge_isNotOk() {
        user.setAge(INVALID_AGE);
        Assertions.assertThrows(UserInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected " + UserInvalidException.class + ", but it wasn`t");
    }

    @Test
    void register_invalidUser_isNotOk() {
        user.setAge(INVALID_AGE);
        user.setLogin(null);
        user.setPassword(INVALID_PASSWORD);
        Assertions.assertThrows(UserInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected " + UserInvalidException.class + ", but it wasn`t");
    }
}
