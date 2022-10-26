package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.DuplicateUserException;
import core.basesyntax.exception.InvalidAgeException;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static String LOGIN = "PERSON";
    private static String PASSWORD = "11111111";
    private static String SHORT_PASSWORD = "1111";
    private static String EMPTY_LOGIN = "";
    private static int AGE = 30;
    private static int MINOR_AGE = 10;
    private static int TALE_AGE = 300;

    @Test
    void nullUser_NotOk() {
        User user = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithNullLogin_NotOk() {
        User user = new User(null, PASSWORD, AGE);
        assertThrows(InvalidLoginException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithEmptyLogin_NotOk() {
        User user = new User(EMPTY_LOGIN, PASSWORD, AGE);
        assertThrows(InvalidLoginException.class, () -> registrationService.register(user));
    }

    @Test
    void duplicateUser_NotOk() {
        User user = new User(LOGIN, PASSWORD, AGE);
        registrationService.register(user);
        assertThrows(DuplicateUserException.class, () -> registrationService.register(user));
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void userWithNullAge_NotOk() {
        User user = new User(LOGIN, PASSWORD, null);
        assertThrows(InvalidAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithMinorAge_NotOk() {
        User user = new User(LOGIN, PASSWORD, MINOR_AGE);
        assertThrows(InvalidAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithTaleAge_NotOk() {
        User user = new User(LOGIN, PASSWORD, TALE_AGE);
        assertThrows(InvalidAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithShortPassword_NotOk() {
        User user = new User(LOGIN, SHORT_PASSWORD, AGE);
        assertThrows(InvalidPasswordException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithValidParameters_Ok() {
        User user = new User(LOGIN, PASSWORD, AGE);
        User actual = registrationService.register(user);
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), user);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
