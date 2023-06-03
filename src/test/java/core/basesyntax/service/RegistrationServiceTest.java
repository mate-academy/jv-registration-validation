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
    private static final String INVALID_PASSWORD = "pswrd";
    private static final String INVALID_LOGIN = "login";
    private static final String EDGE_PASSWORD = "paswrd";
    private static final String EDGE_LOGIN = "newlog";
    private static final int INVALID_AGE = 17;
    private static final int EDGE_AGE = 18;
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
        Storage.people.clear();
    }

    @Test
    void invalidPassword_NotOk() {
        user = new User(VALID_LOGIN, INVALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void invalidLogin_NotOk() {
        user = new User(INVALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void theAgeIsLowerThanEighteen_NotOk() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void theAgeIsEighteen_Ok() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, EDGE_AGE);
        assertEquals(18, user.getAge());
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void validUser_Ok() {
        user = new User(VALID_LOGIN,VALID_PASSWORD, VALID_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void passwordLengthEqualsSix_Ok() {
        user = new User(VALID_LOGIN,EDGE_PASSWORD,VALID_AGE);
        assertEquals(6, user.getPassword().length());
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void passwordLengthEqualsZero_NotOk() {
        user = new User(VALID_LOGIN, EMPTY_STRING,VALID_AGE);
        assertEquals(0, user.getPassword().length());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginLengthEqualsSix_Ok() {
        user = new User(EDGE_LOGIN,VALID_PASSWORD,VALID_AGE);
        assertEquals(6, user.getLogin().length());
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void loginLengthEqualsZero_NotOk() {
        user = new User(EMPTY_STRING,VALID_PASSWORD, VALID_AGE);
        assertEquals(0, user.getLogin().length());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void userNotUnique_NotOk() {
        user = new User(VALID_LOGIN,VALID_PASSWORD,VALID_AGE);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(new User(VALID_LOGIN,VALID_PASSWORD,VALID_AGE)));
    }

    @Test
    void loginEqualsNull_NotOk() {
        user = new User(null,VALID_PASSWORD,VALID_AGE);
        assertNull(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordEqualsNull_NotOk() {
        user = new User(VALID_LOGIN, null, VALID_AGE);
        assertNull(user.getPassword());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void ageEqualsNull_NotOk() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, null);
        assertNull(user.getAge());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void userEqualsNull_NotOk() {
        user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
