package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.CustomException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationValidator;
    private static StorageDao storageDao;
    private static User userIsValid;
    private static User invalidUser;

    @BeforeAll
    static void beforeAll() {
        registrationValidator = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        userIsValid = new User();
        invalidUser = new User();
    }

    @BeforeEach
    void setUp() {
        userIsValid.setId(77L);
        userIsValid.setLogin("Goodvin");
        userIsValid.setPassword("Password123");
        userIsValid.setAge(27);

        invalidUser.setId(-123L);
        invalidUser.setLogin(null);
        invalidUser.setPassword("1");
        invalidUser.setAge(13);
    }

    @Test
    void register_null_user_notOk() {
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(null);
        }, "User cannot be null!");
    }

    @Test
    void register_user_with_such_login_is_already_added_notOk() {
        storageDao.add(userIsValid);
        assertEquals(storageDao.get(userIsValid.getLogin()), userIsValid);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "User with the same Login is already exist!");
    }

    @Test
    void register_age_more_than_18_notOk() {
        userIsValid.setAge(119);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Age should be above 18 or equal and less than 115");
    }

    @Test
    void register_age_less_than_18_notOk() {
        userIsValid.setAge(14);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Age should be above 18 or equal and less than 115");
    }

    @Test
    void register_age_less_than_0_notOk() {
        userIsValid.setAge(-19);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Age cannot be a negative number and should be above 18 or equal and less than 115");
    }

    @Test
    void register_user_with_login_that_exist_whitespace_notOk() {
        userIsValid.setLogin("Good vin");
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Your Login cannot exist whitespace!");
    }

    @Test
    void register_login_is_empty_notOk() {
        userIsValid.setLogin("");
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Your Login should exist at least 1 character!");
    }

    @Test
    void register_password_less_than_6_characters_notOk() {
        userIsValid.setPassword("pas");
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Your Password should include 6 and more characters!");
    }

    @Test
    void register_password_contains_special_characters_notOk() {
        userIsValid.setPassword("^$#@#($%6*");
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "Your Password shouldn't contains special characters!");
    }

    @Test
    void register_user_with_null_password_notOk() {
        User newUser = new User();
        newUser.setId(7712L);
        newUser.setLogin("Goodvin");
        newUser.setAge(18);
        assertThrows(CustomException.class, () -> {
            registrationValidator.register(userIsValid);
        }, "You must enter Your Password!");
    }
}
