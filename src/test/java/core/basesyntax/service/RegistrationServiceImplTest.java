package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 25;
    private static final int AGE_GREATER_THAN_MAX = 127;
    private static final int AGE_LESS_THAN_MIN = 17;
    private static final int NEGATIVE_AGE = -999;
    private static final String DEFAULT_PASSWORD = "default_password";
    private static final String DEFAULT_LOGIN = "default_login";
    private static final String EMPTY_LOGIN = "";
    private static final String INVALID_PASSWORD = "1234";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_LOGIN);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for login is null");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin(EMPTY_LOGIN);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for empty login");
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for password is null");
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        },"InvalidRegistrationDataException expected for password length is less than 6");
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for age is null");
    }

    @Test
    void register_ageLessThan18_notOk() {
        user.setAge(AGE_LESS_THAN_MIN);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for age is less than 18");
    }

    @Test
    void register_ageMoreThan120_notOk() {
        user.setAge(AGE_GREATER_THAN_MAX);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for age is more than 120");
    }

    @Test
    void register_ageIsNegative_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for age is negative.");
    }

    @Test
    void register_userAlreadyInStorage_notOk() {
        Storage.people.add(user);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for user that already registered.");
    }

    @Test
    void register_nullValue_NotOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(null);
        },"InvalidRegistrationDataException expected for user is null");
    }

    @Test
    void register_user_ok() {
        registrationService.register(user);
        assertEquals(Storage.people.size(), user.getId());
        assertEquals(DEFAULT_LOGIN, user.getLogin());
        assertEquals(DEFAULT_PASSWORD, user.getPassword());
        assertEquals(DEFAULT_AGE, user.getAge());
    }
}
