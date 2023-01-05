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
    void checkEmptyPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Password value can't be NULL"
        );
    }

    @Test
    void checkEmptyEmail_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "E-mail value can't be NULL"
        );
    }

    @Test
    void checkEmptyAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Age value can't be NULL"
        );
    }

    @Test
    void checkExistedEmail_notOk() {
        registrationService.register(user);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "User is already exists!"
        );
    }

    @Test
    void ageIsBelowZero_notOk() {
        user.setAge(-1);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Age value is below 0!"
        );
    }

    @Test
    void ageIsZero_notOk() {
        user.setAge(0);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Age value is 0!"
        );
    }

    @Test
    void ageIsUnderTheMinValue_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Age value is smaller "
                + "than excepted! Access denied"
        );
    }

    @Test
    void passwordIsZero_notOk() {
        user.setPassword("");
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Password can't be empty!"
        );
    }

    @Test
    void passwordIsLessThanSixSymbols_notOk() {
        user.setPassword("4534");
        assertThrows(InvalidUserException.class, () ->
                registrationService.register(user), "Password can't be less than 6 symbols!"
        );
    }

    @Test
    void userIsAdded_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }
}
