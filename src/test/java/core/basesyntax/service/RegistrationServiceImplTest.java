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
    void register_loginNull_notOk() {
        User actual = new User(null, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_passwordInvalid_notOk() {
        User actual = new User("useruser1", "123", DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_ageInvalid_notOk() {
        User actual = new User("useruser1", DEFAULT_PASSWORD, 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginRegistered_notOk() {
        User actual = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginNullAndPasswordInvalid_notOk() {
        User actual = new User(null, "123", DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginNullAndAgeInvalid_notOk() {
        User actual = new User(null, DEFAULT_PASSWORD, 15);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginRegisteredAndPasswordInvalid_notOk() {
        User actual = new User(DEFAULT_LOGIN, "123", DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginRegisteredAndAgeInvalid_notOk() {
        User actual = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_passwordAndAgeInvalid_notOk() {
        User actual = new User("useruser1", "123", 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginNullPasswordAndAgeInvalid_notOk() {
        User actual = new User(null, "123", 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginRegisteredPasswordAndAgeInvalid_notOk() {
        User actual = new User(DEFAULT_LOGIN, "123", 16);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_validUser_ok() {
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
