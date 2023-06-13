package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final String SHORT_PASSWORD = "pswrd";
    private static final String SHORT_LOGIN = "login";
    private static final String MIN_LENGTH_PASSWORD = "paswrd";
    private static final String MIN_LENGTH_LOGIN = "newlog";
    private static final int BELOW_MIN_AGE = 17;
    private static final int MIN_AGE = 18;
    private static final String VALID_PASSWORD = "TheStrongestPassword";
    private static final String VALID_LOGIN = "NewUser";
    private static final int VALID_AGE = 30;
    private static final String EMPTY_STRING = "";
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.clear();
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin(SHORT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_theAgeIsLowerThanEighteen_notOk() {
        user.setAge(BELOW_MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_theAgeIsEighteen_ok() {
        user.setAge(MIN_AGE);
        assertEquals(18, user.getAge());
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_validUser_ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthEqualsSix_Ok() {
        user.setPassword(MIN_LENGTH_PASSWORD);
        assertEquals(6, user.getPassword().length());
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthEqualsZero_notOk() {
        user.setPassword(EMPTY_STRING);
        assertEquals(0, user.getPassword().length());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthEqualsSix_ok() {
        user.setLogin(MIN_LENGTH_LOGIN);
        assertEquals(6, user.getLogin().length());
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_loginLengthEqualsZero_notOk() {
        user.setLogin(EMPTY_STRING);
        assertEquals(0, user.getLogin().length());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNotUnique_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_loginEqualsNull_notOk() {
        user.setLogin(null);
        assertNull(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordEqualsNull_notOk() {
        user.setPassword(null);
        assertNull(user.getPassword());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageEqualsNull_notOk() {
        user.setAge(null);
        assertNull(user.getAge());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userEqualsNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }
}
