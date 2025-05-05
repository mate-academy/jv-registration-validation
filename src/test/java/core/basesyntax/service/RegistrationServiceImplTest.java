package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALIDATION_EXCEPTION_MESSAGE =
            "ValidationException should be thrown in this case";
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 18;
    private static RegistrationService registrationService;
    private static User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user), VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user), VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user), VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    void register_shortLogin_NotOk() {
        User user = new User("one", VALID_PASSWORD, VALID_AGE);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user), VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    void register_registerUserWithLoginWhichAlreadyExist_NotOk() {
        Storage.people.add(validUser);
        User user = new User(VALID_LOGIN, "password", 22);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user), VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    void register_shortPassword_NotOk() {
        User user = new User(VALID_LOGIN, "pass", VALID_AGE);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user), VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    void register_underAgeUser_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, 17);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user), VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, -20);
        assertThrows(ValidationException.class, () ->
                registrationService.register(user), VALIDATION_EXCEPTION_MESSAGE);
    }

    @Test
    void register_addValidUser_Ok() {
        registrationService.register(validUser);
        boolean actual = Storage.people.contains(validUser);
        assertTrue(actual);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
