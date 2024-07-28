package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "login";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_MIN_AGE = 18;
    private static final int INVALID_NEGATIVE_AGE = -1;
    private static final int INVALID_AGE = 16;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_MIN_AGE);
        user.setLogin("svitlanakozak12332@gmail.com");
        user.setPassword("987654");
        user.setAge(38);
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
        User user = new User("svitlanakozak12332@gmail.com", "987654", 38);
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
        User user = new User("lana", VALID_PASSWORD, VALID_MIN_AGE);
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
        User user = new User(VALID_LOGIN, "456", VALID_MIN_AGE);
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
