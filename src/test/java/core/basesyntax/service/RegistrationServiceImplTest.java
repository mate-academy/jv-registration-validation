package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationServiceException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String VALID_LOGIN = "123456";
    private static final String VALID_PASSWORD = "654321";

    private static RegistrationService registrationService;
    private User user = new User(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);
    }

    @Test
    void register_UserAlreadyExists_notOk() {
        String expected = "User with login: "
                + user.getLogin()
                + " has already registered!";
        Storage.people.add(user);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_LoginIsNull_notOk() {
        String expected = "User login can not be least then "
                + MIN_LENGTH
                + " chars!";
        user.setLogin(null);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_PasswordIsNull_notOk() {
        String expected = "User password can not be least then "
                + MIN_LENGTH
                + " chars!";
        user.setPassword(null);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_AgeIsIncorrect_notOk() {
        String expected = "User must be of legal age!";
        user.setAge(-100);
        assertEquals(expected, assertException(user).getMessage());
        user.setAge(5);
        assertEquals(expected, assertException(user).getMessage());
        user.setAge(3);
        assertEquals(expected, assertException(user).getMessage());
        user.setAge(16);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_LoginIsIncorrect_notOk() {
        String expected = "User login can not be least then "
                + MIN_LENGTH
                + " chars!";
        user.setLogin("");
        assertEquals(expected, assertException(user).getMessage());
        user.setLogin("SAM");
        assertEquals(expected, assertException(user).getMessage());
        user.setLogin("LOSHA");
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_PasswordIsIncorrect_notOk() {
        String expected = "User password can not be least then "
                + MIN_LENGTH
                + " chars!";
        user.setPassword("");
        assertEquals(expected, assertException(user).getMessage());
        user.setPassword("123");
        assertEquals(expected, assertException(user).getMessage());
        user.setPassword("qwert");
        assertEquals(expected, assertException(user).getMessage());
    }

    private RegistrationServiceException assertException(User user) {
        return assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user));
    }
}
