package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "my_valid_login";
    private static final String VALID_PASSWORD = "valid#$PASS@!1234";
    private static final int MINIMUM_LENGTH = 6;
    private static final int VALID_AGE_MINIMUM = 18;
    private static final int AGE_ZERO_INVALID = 0;

    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE_MINIMUM);
    }

    @Test
    void register_UserRegistered_Ok() {
        assertEquals(user, registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_UserIsNull_notOk() {
        String expected = "User can't be null!";
        assertEquals(expected, assertException(null).getMessage());
    }

    @Test
    void register_UserAlreadyExists_notOk() {
        String expected = "User with this login - "
                + user.getLogin()
                + " - already registered. Try to log in";
        Storage.people.add(user);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_LoginIsNull_notOk() {
        String expected = "Your login is shorter than "
                + MINIMUM_LENGTH + "or not specified";
        user.setLogin(null);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_PasswordIsNull_notOk() {
        String expected = "Your password is shorter than "
                + MINIMUM_LENGTH + " or not specified";
        user.setPassword(null);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_AgeIsIncorrect_notOk() {
        String expected = "User's age is less than "
                + VALID_AGE_MINIMUM + " or not specified";

        user.setAge(AGE_ZERO_INVALID);
        assertEquals(expected, assertException(user).getMessage());

        user.setAge(-14);
        assertEquals(expected, assertException(user).getMessage());

        user.setAge(-1);
        assertEquals(expected, assertException(user).getMessage());

        user.setAge(7);
        assertEquals(expected, assertException(user).getMessage());

        user.setAge(17);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_LoginIsIncorrect_notOk() {
        String expected = "Your login is shorter than "
                + MINIMUM_LENGTH + "or not specified";
        user.setLogin("");
        assertEquals(expected, assertException(user).getMessage());

        user.setLogin("smth");
        assertEquals(expected, assertException(user).getMessage());

        user.setLogin("login");
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_PasswordIsIncorrect_notOk() {
        String expected = "Your password is shorter than "
                + MINIMUM_LENGTH + " or not specified";
        user.setPassword("");
        assertEquals(expected, assertException(user).getMessage());

        user.setPassword("123");
        assertEquals(expected, assertException(user).getMessage());

        user.setPassword("1Psa@");
        assertEquals(expected, assertException(user).getMessage());
    }

    private InvalidInputDataException assertException(User user) {
        return assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(user));
    }
}
