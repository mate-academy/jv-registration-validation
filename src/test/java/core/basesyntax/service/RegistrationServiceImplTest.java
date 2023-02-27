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
    private static final String INITIAL_LOGIN = "initial_login";
    private static final String INITIAL_PASSWORD = "initial_password";
    private static final String NON_UNIQUE_LOGIN = "second_login";
    private static final int AGE_18 = 18;
    private static final int INITIAL_AGE = 24;
    private static RegistrationService registrationService;
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User();
        defaultUser.setAge(90);
        defaultUser.setLogin(INITIAL_LOGIN);
        defaultUser.setPassword(INITIAL_PASSWORD);
        defaultUser.setAge(INITIAL_AGE);
    }

    @AfterEach
    void after() {
        Storage.people.clear();
    }

    @Test
    void register_nullArgument_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(null);
        }, "InvalidRegistrationDataException expected for null argument");
    }

    @Test
    void register_loginIsNull_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.login = null");
    }

    @Test
    void register_ageIsNull_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.age = null");
    }

    @Test
    void register_passwordIsNull_notOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.password = null");
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        User storageUser = new User();
        storageUser.setLogin(NON_UNIQUE_LOGIN);
        Storage.people.add(storageUser);
        defaultUser.setLogin(NON_UNIQUE_LOGIN);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, InvalidRegistrationDataException.class.getName()
                + ", method does not allow user login duplicates");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        defaultUser.setLogin("");
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException,"
                + "when login field empty an exception should be thrown");
    }

    @Test
    void register_default_ok() {
        registrationService.register(defaultUser);
        assertEquals(INITIAL_LOGIN, defaultUser.getLogin());
        assertEquals(1, defaultUser.getId());
        assertEquals(INITIAL_PASSWORD, defaultUser.getPassword());
        assertEquals(INITIAL_AGE, defaultUser.getAge());
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        defaultUser.setPassword("");
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException,"
                + "if password length less than 6 an exception should be thrown");
    }

    @Test
    void register_ageLessThan18_notOk() {
        defaultUser.setAge(16);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException, if age < 18 method you should thrown an exception");
    }

    @Test
    void register_ageEquals18_ok() {
        defaultUser.setAge(AGE_18);
        registrationService.register(defaultUser);
        assertEquals(AGE_18,
                defaultUser.getAge(), "Age not match");
    }

    @Test
    void register_ageIsNegative_notOk() {
        defaultUser.setAge(-23939);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException, if age < 0 method should thrown an exception");
    }

    @Test
    void register_ageGreaterThan140_notOk() {
        defaultUser.setAge(999);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException, if age > 140 method should thrown an exception");
    }
}
