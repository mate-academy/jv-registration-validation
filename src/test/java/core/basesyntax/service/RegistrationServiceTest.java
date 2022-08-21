package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
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
    private static final Integer AGE_TOO_BIG = 151;

    private static RegistrationService registrationService;
    private User newUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        newUser = new User();
        newUser.setLogin(LOGIN);
        newUser.setPassword(PASSWORD);
        newUser.setAge(AGE);
    }

    @Test
    @Order(1)
    void register_userLoginIsNull_notOk() {
        newUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(2)
    void register_userLoginLengthTooShort_notOk() {
        newUser.setLogin(LOGIN_TOO_SHORT);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(2)
    void register_userLoginLengthTooLong_notOk() {
        newUser.setLogin(LOGIN_TOO_LONG);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(1)
    void register_userPasswordIsNull_notOk() {
        newUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(3)
    void register_userPasswordIsTooShort_notOk() {
        newUser.setPassword(PASSWORD_TOO_SHORT);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(3)
    void register_userPasswordIsTooLong_notOk() {
        newUser.setPassword(PASSWORD_TOO_LONG);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(1)
    void register_userAgeIsNull_notOk() {
        newUser.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(4)
    void register_userAgeIsTooSmall_notOk() {
        newUser.setAge(AGE_TOO_SMALL);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(4)
    void register_userAgeIsTooOld_notOk() {
        newUser.setAge(AGE_TOO_BIG);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    @Order(5)
    void register_UserAlreadyExists_notOk() {
        int expected = 0;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
        registrationService.register(newUser);
        expected = 1;
        actual = Storage.people.size();
        assertEquals(expected, actual);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
        assertEquals(expected, actual);
    }
}
