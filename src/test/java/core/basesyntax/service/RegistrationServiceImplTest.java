package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_EMAIL = "test_email@gmail.com";
    private static RegistrationService registrationService;
    private static User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(MIN_AGE);
        user.setPassword(DEFAULT_PASSWORD);
        user.setLogin(DEFAULT_EMAIL);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Password value can't be NULL"
        );
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "E-mail value can't be NULL"
        );
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Age value can't be NULL"
        );
    }

    @Test
    void register_loginExisted_notOk() {
        registrationService.register(user);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "User is already exists!"
        );
    }

    @Test
    void register_ageIsBelowZero_notOk() {
        user.setAge(-1);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Age value is below 0!"
        );
    }

    @Test
    void register_ageIsZero_notOk() {
        user.setAge(0);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Age value is 0!"
        );
    }

    @Test
    void register_ageIsUnderTheMinValue_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Age value is smaller "
                + "than excepted! Access denied"
        );
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword("");
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Password can't be empty!"
        );
    }

    @Test
    void register_passwordIsLessThanSixSymbols_notOk() {
        user.setPassword("4534");
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Password can't be less than 6 symbols!"
        );
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
