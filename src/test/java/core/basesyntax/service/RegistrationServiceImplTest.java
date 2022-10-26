package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String LOGIN = "PERSON";
    private static final String PASSWORD = "111111";
    private static final String SHORT_PASSWORD = "11111";
    private static final String EMPTY_LOGIN = "";
    private static final int AGE = 18;
    private static final int MINOR_AGE = 17;
    private static final int TALE_AGE = 121;
    private User user;
    private User nullUser;
    private User userWithNullLogin;
    private User userWithEmptyLogin;
    private User userWithNullAge;
    private User userWithMinorAge;
    private User userWithTaleAge;
    private User userWithShortPassword;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(LOGIN, PASSWORD, AGE);
        nullUser = null;
        userWithNullLogin = new User(null, PASSWORD, AGE);
        userWithEmptyLogin = new User(EMPTY_LOGIN, PASSWORD, AGE);
        userWithNullAge = new User(LOGIN, PASSWORD, null);
        userWithMinorAge = new User(LOGIN, PASSWORD, MINOR_AGE);
        userWithTaleAge = new User(LOGIN, PASSWORD, TALE_AGE);
        userWithShortPassword = new User(LOGIN, SHORT_PASSWORD, AGE);
    }

    @Test
    void nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void userWithNullLogin_notOk() {
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void userWithEmptyLogin_notOk() {
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(userWithEmptyLogin));
    }

    @Test
    void duplicateUser_notOk() {
        registrationService.register(user);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void userWithNullAge_notOk() {
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(userWithNullAge));
    }

    @Test
    void userWithMinorAge_notOk() {
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(userWithMinorAge));
    }

    @Test
    void userWithTaleAge_notOk() {
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(userWithTaleAge));
    }

    @Test
    void userWithShortPassword_notOk() {
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(userWithShortPassword));
    }

    @Test
    void userWithValidParameters_ok() {
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
