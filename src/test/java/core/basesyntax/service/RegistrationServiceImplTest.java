package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "user_login";
    private static final String VALID_PASSWORD = "123456";
    private static final int VALID_AGE = 18;
    private static final Class EXCEPTION_CLASS = RegistrationException.class;
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUser() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        Assertions.assertThrows(EXCEPTION_CLASS,
                () -> registrationService.register(null),
                "Should throw RegistrationException if user is null");
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(EXCEPTION_CLASS,
                () -> registrationService.register(user),
                "Should throw RegistrationException if user login is null");
    }

    @Test
    void register_userLoginAlreadyExists_notOk() {
        registrationService.register(user);
        Assertions.assertThrows(EXCEPTION_CLASS,
                () -> registrationService.register(user),
                "Should throw RegistrationException if user login already exists");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(EXCEPTION_CLASS,
                () -> registrationService.register(user),
                "Should throw RegistrationException if user password is null");
    }

    @Test
    void register_userPasswordLengthIsLessThanMinLength_notOk() {
        user.setPassword("12345");
        Assertions.assertThrows(EXCEPTION_CLASS,
                () -> registrationService.register(user),
                "Should throw RegistrationException when user password length is less than ");
    }

    @Test
    void register_userPasswordLengthIsMinLength_ok() {
        user.setPassword(VALID_PASSWORD);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual, "Should add user if user password is 6 symbols");
    }

    @Test
    void register_userPasswordLengthIsLongerThanMinLength_ok() {
        user.setPassword(VALID_PASSWORD + "7");
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual, "Should add user if user password is longer than 6 symbols");
    }

    @Test
    void register_userAgeIsNull_notOk() {
        user.setAge(null);
        Assertions.assertThrows(EXCEPTION_CLASS,
                () -> registrationService.register(user),
                "Should throw RegistrationException if user age is null");
    }

    @Test
    void register_ageLessThanValid_notOK() {
        user.setAge(VALID_AGE - 1);
        assertThrows(EXCEPTION_CLASS,() -> registrationService.register(user),
                "Should throw RegistrationException if user age is below " + VALID_AGE);
    }

    @Test
    void register_ageIsValid_OK() {
        user.setAge(VALID_AGE);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual, "User should be added if age is " + VALID_AGE);
    }

    @Test
    void register_ageMoreThanValid_OK() {
        user.setAge(VALID_AGE + 1);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual, "User should be added if age is greater than " + VALID_AGE);
    }

    @Test
    void register_validUserIsAddedToStorage_Ok() {
        registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        Assertions.assertEquals(expected, user, "Should add users if all fields are valid");
    }
}
