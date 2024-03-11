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
    private static final String NOT_VALID_LOGIN = "JAVA";
    private static final String NOT_VALID_PASSWORD = "1234";
    private static final int NOT_VALID_AGE = 13;



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
    void added_UserToStorage_Ok() {
        assertEquals(user, registrationService.register(user));
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
        user.setAge(NOT_VALID_AGE);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_LoginIsIncorrect_notOk() {
        String expected = "User login can not be least then "
                + MIN_LENGTH
                + " chars!";
        user.setLogin(NOT_VALID_LOGIN);
        assertEquals(expected, assertException(user).getMessage());
    }

    @Test
    void register_PasswordIsIncorrect_notOk() {
        String expected = "User password can not be least then "
                + MIN_LENGTH
                + " chars!";
        user.setPassword(NOT_VALID_PASSWORD);
        assertEquals(expected, assertException(user).getMessage());
    }

    private RegistrationServiceException assertException(User user) {
        return assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user));
    }
}
