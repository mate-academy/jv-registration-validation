package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private static final int VALID_AGE = 28;
    private static final String VALID_LOGIN = "userlogincorrect";
    private static final String INVALID_LOGIN = "invalide_login";
    private static final String VALID_PASSWORD = "userpasswordvalid";
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_nullArgument_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(null);
        }, "InvalidRegistrationDataException expected for null argument");
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for user.login = null");
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for user.age = null");
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for user.password = null");
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        User storageUser = new User();
        storageUser.setLogin(INVALID_LOGIN);
        Storage.people.add(storageUser);
        user.setLogin(INVALID_LOGIN);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, InvalidRegistrationDataException.class.getName()
                + ", method does not allow user login duplicates");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException,"
                + "when login field empty an exception should be thrown");
    }

    @Test
    void register_default_ok() {
        registrationService.register(user);
        assertEquals(VALID_LOGIN, user.getLogin());
        assertEquals(1, user.getId());
        assertEquals(VALID_PASSWORD, user.getPassword());
        assertEquals(VALID_AGE, user.getAge());
    }

    @Test
    void register_passwordLengthLessThanMin_notOk() {
        user.setPassword("");
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException,"
                + "if password length less than 6 an exception should be thrown");
    }

    @Test
    void register_ageLessThan18_notOk() {
        user.setAge(16);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException, if age < 18 method you should thrown an exception");
    }

    @Test
    void register_ageEquals18_ok() {
        user.setAge(MIN_AGE);
        registrationService.register(user);
        assertEquals(MIN_AGE,
                user.getAge(), "Age not match");
    }

    @Test
    void register_ageIsNegative_notOk() {
        user.setAge(-999);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException, if age < 0 method should thrown an exception");
    }

    @Test
    void register_ageGreaterThan116_notOk() {
        user.setAge(88888);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException, if age > 116 method should thrown an exception");
    }
}

