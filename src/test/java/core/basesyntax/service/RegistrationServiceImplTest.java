package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.NoValidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_USER_LOGIN = "Spider";
    private static final String INVALID_USER_LOGIN_LENGHT = "Mario";
    private static final String INVALID_CHARACTERS_IN_USER_LOGIN = "Spider24";
    private static final String VALID_USER_PASSWORD = "1234qw";
    private static final String INVALID_USER_PASSWORD_LENGHT = "1234q";
    private static final String INVALID_CHARACTERS_IN_USER_PASSWORD = "1322/*6gg-";
    private static final int VALID_USER_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin(VALID_USER_LOGIN);
        user.setPassword(VALID_USER_PASSWORD);
        user.setAge(VALID_USER_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "This user is null. Please set all fields for users";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void register_userWithValidData_Ok() {
        registrationService.register(user);
        User actual = Storage.people.get(0);
        User expected = user;
        assertEquals(expected,actual);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "You can't create account without login."
                + " Please, set your login";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void register_notValidLoginLength_notOk() {
        user.setLogin(INVALID_USER_LOGIN_LENGHT);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Incorrect login length. "
                + "Login must contain at least " + 6
                + "  and less then " + 20 + " characters";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void register_invalidCharacterInLogin_notOk() {
        user.setLogin(INVALID_CHARACTERS_IN_USER_LOGIN);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Login must contain only characters";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "You can't create account without password."
                + " Please, set your password";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        user.setPassword(INVALID_USER_PASSWORD_LENGHT);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Incorrect password length. "
                + "Login must contain at least " + 6
                + "  and less then " + 20 + " characters";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void register_invalidCharacterInPassword_notOk() {
        user.setPassword(INVALID_CHARACTERS_IN_USER_PASSWORD);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Password must contain only characters and digits";
        assertEquals(expected, exception.getMessage());
    }

    void register_nullAge_notOk() {
        user.setAge(null);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "You can't add user without age."
                + " Please, enter you age";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void register_invaligAge_notOk() {
        user.setAge(INVALID_AGE);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Service is available for 18+ only";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void redister_userWithSameLogin_notOk() {
        registrationService.register(user);
        NoValidDataException exception = assertThrows(NoValidDataException.class, () -> {
            registrationService.register(user);
        });
        String expected = "User with this login already exist";
        assertEquals(expected, exception.getMessage());
    }
}
