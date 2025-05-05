package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "login";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_MIN_AGE = 18;
    private static final int INVALID_NEGATIVE_AGE = -1;
    private static final int INVALID_AGE = 16;
    private static final String ACTUAL_LOGIN = "svitlanakozak12332@gmail.com";
    private static final String ACTUAL_PASSWORD = "987654";
    private static final int ACTUAL_AGE = 38;
    private static final String INVALID_LOGIN = "lana";
    private static final String INVALID_PASSWORD = "456";
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_validUser_ok() {
        User user = new User(ACTUAL_LOGIN, ACTUAL_PASSWORD, ACTUAL_AGE);
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_nullUserLogin_notOk() {
        User user = new User(null, VALID_PASSWORD, VALID_MIN_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortUserLogin_notOk() {
        User user = new User(INVALID_LOGIN, VALID_PASSWORD, VALID_MIN_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullUserPassword_notOk() {
        User user = new User(VALID_LOGIN, null, VALID_MIN_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortUserPassword_notOk() {
        User user = new User(VALID_LOGIN, INVALID_PASSWORD, VALID_MIN_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_NEGATIVE_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userAgeIsLessThanAllowed_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userExist_notOk() {
        User newUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_MIN_AGE);
        Storage.people.add(newUser);
        User userExist = new User(VALID_LOGIN, VALID_PASSWORD, VALID_MIN_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userExist));
    }
}
