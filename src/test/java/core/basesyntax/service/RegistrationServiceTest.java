package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationServiceTest {
    private static final String LOGIN = "login";
    private static final String LOGIN_TOO_SHORT = "abc";
    private static final String LOGIN_TOO_LONG = "abcdefghijklmnopqrstuvwxyzABCDE";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_TOO_SHORT = "abc";
    private static final String PASSWORD_TOO_LONG = "abcdefghijklmnopqrstuvwxyzABCDE";
    private static final Integer AGE = 18;
    private static final Integer AGE_TOO_SMALL = 17;
    private static final Integer AGE_IS_TOO_OLD = 151;

    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User();
        newUser.setLogin(LOGIN);
        newUser.setPassword(PASSWORD);
        newUser.setAge(AGE);
    }

    @Test
    @Order(1)
    void userLoginIsNull_notOk() {
        newUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(2)
    void userLoginLengthTooShort_notOk() {
        newUser.setLogin(LOGIN_TOO_SHORT);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(2)
    void userLoginLengthTooLong_notOk() {
        newUser.setLogin(LOGIN_TOO_LONG);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(1)
    void userPasswordIsNull_notOk() {
        newUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(3)
    void userPasswordIsTooShort_notOk() {
        newUser.setPassword(PASSWORD_TOO_SHORT);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(3)
    void userPasswordIsTooLong_notOk() {
        newUser.setPassword(PASSWORD_TOO_LONG);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(1)
    void userAgeIsNull_notOk() {
        newUser.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(4)
    void userAgeIsTooSmall_notOk() {
        newUser.setAge(AGE_TOO_SMALL);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(4)
    void userAgeIsTooOld_notOk() {
        newUser.setAge(AGE_IS_TOO_OLD);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    @Order(5)
    void registerUserThatAlreadyExists_notOk() {
        registrationService.register(newUser);
        String loginOfUserThatAleadyExists = Storage.people.get(0).getLogin();
        newUser.setLogin(loginOfUserThatAleadyExists);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }
}
