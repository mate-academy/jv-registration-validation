package core.basesyntax.service;

import core.basesyntax.CustomException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "Goodvin";
    private static final String LOGIN_FOR_TEST_SAME_LOGIN = "Same_login";
    private static final String DEFAULT_PASSWORD = "Password123";
    private static final int DEFAULT_AGE = 27;
    private static final int ADDED_FIRST_ID = 1;
    private static RegistrationService registrationValidator;
    private static StorageDao storageDao;
    private static User userIsValid;

    @BeforeAll
    static void beforeAll() {
        registrationValidator = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        userIsValid = new User();
    }

    @BeforeEach
    void setUp() {
        userIsValid.setLogin(DEFAULT_LOGIN);
        userIsValid.setPassword(DEFAULT_PASSWORD);
        userIsValid.setAge(DEFAULT_AGE);
    }

    @Test
    void register_null_user_notOk() {
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(null);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the null user. Null user is allowed");
    }

    @Test
    void register_user_with_such_login_is_already_added_notOk() {
        User newUser = new User();
        newUser.setLogin(LOGIN_FOR_TEST_SAME_LOGIN);
        Storage.people.add(newUser);
        userIsValid.setLogin(LOGIN_FOR_TEST_SAME_LOGIN);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with the same name. "
                + "User with the same Login is already exist!");
    }

    @Test
    void register_age_more_than_18_notOk() {
        userIsValid.setAge(119);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with age more than 115. "
                + "Age cannot be a negative number or contains special symbols"
                + " and should be above 18 or equal and less than 115");
    }

    @Test
    void register_age_less_than_18_notOk() {
        userIsValid.setAge(14);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with age less than 18. "
                + "Age cannot be a negative number or contains special symbols"
                + " and should be above 18 or equal and less than 115");
    }

    @Test
    void register_age_less_than_0_notOk() {
        userIsValid.setAge(-19);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with negative number at his age. "
                + "Age cannot be a negative number or contains special symbols"
                + " and should be above 18 or equal and less than 115");
    }

    @Test
    void register_user_with_login_that_exist_whitespace_notOk() {
        userIsValid.setLogin("Good vin");
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with whitespace at his login. "
                + "Your Login cannot exist whitespace!");
    }

    @Test
    void register_login_is_empty_notOk() {
        userIsValid.setLogin("");
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with empty login. "
                + "Your Login should exist at least 1 character!");
    }

    @Test
    void register_password_less_than_6_characters_notOk() {
        userIsValid.setPassword("pas");
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with password length less than 6 characters. "
                + "Your Password should include 6 and more characters!");
    }

    @Test
    void register_password_contains_special_characters_notOk() {
        userIsValid.setPassword("^$#@#($%6*");
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with password which contains special symbols. "
                + "Your Password shouldn't contains special characters!");
    }

    @Test
    void register_user_with_null_password_notOk() {
        User newUser = new User();
        newUser.setLogin(DEFAULT_LOGIN);
        newUser.setPassword(null);
        newUser.setAge(DEFAULT_AGE);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(newUser);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with empty password. "
                + "You must enter Your Password!");
    }

    @Test
    void register_user_with_null_login_notOk() {
        User newUser = new User();
        newUser.setPassword(DEFAULT_PASSWORD);
        newUser.setAge(DEFAULT_AGE);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(newUser);
        }, "Expected " + CustomException.class.getName()
                + " to be thrown for the user with empty login. "
                + "You must enter Your Login!");
    }

    @Test
    void register_user_Ok() {
        registrationValidator.register(userIsValid);
        assertEquals(userIsValid.getLogin(), DEFAULT_LOGIN);
        assertEquals(userIsValid.getPassword(), DEFAULT_PASSWORD);
        assertEquals(userIsValid.getAge(), DEFAULT_AGE);
        assertEquals(userIsValid.getId(), ADDED_FIRST_ID);
    }
}
