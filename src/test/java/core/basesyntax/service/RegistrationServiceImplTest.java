package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "unknownCactus";
    private static final String DEFAULT_PASSWORD = "user111";
    private static final int DEFAULT_AGE = 18;
    private static final String EXCEPTION = InvalidInputDataException.class.toString();
    private static RegistrationServiceImpl registrationService;
    private static User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        registrationService.register(user);
    }

    @Test
    void register_loginNull_NotOk() {
        actual = new User(null, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_passwordInvalid_NotOk() {
        actual = new User("useruser1", "123", DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_ageInvalid_NotOk() {
        actual = new User("useruser1", DEFAULT_PASSWORD, 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginRegistered_NotOk() {
        actual = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginNullAndPasswordInvalid_NotOk() {
        actual = new User(null, "123", DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginNullAndAgeInvalid_NotOk() {
        actual = new User(null, DEFAULT_PASSWORD, 15);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginRegisteredAndPasswordInvalid_NotOk() {
        actual = new User(DEFAULT_LOGIN, "123", DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginRegisteredAndAgeInvalid_NotOk() {
        actual = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_passwordAndAgeInvalid_NotOk() {
        actual = new User("useruser1", "123", 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginNullPasswordAndAgeInvalid_NotOk() {
        actual = new User(null, "123", 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginRegisteredPasswordAndAgeInvalid_NotOk() {
        actual = new User(DEFAULT_LOGIN, "123", 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_validUser_Ok() {
        User user = new User("useruser1", DEFAULT_PASSWORD, DEFAULT_AGE);
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual,
                "User added");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
