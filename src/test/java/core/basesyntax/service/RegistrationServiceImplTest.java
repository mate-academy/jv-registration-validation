package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String EXISTING_LOGIN = "existingLogin";
    private static final String SHORT_LOGIN = "short";
    private static final String SHORT_PASSWORD = "short";
    private static final String NULL_LOGIN_MESSAGE = "Login cannot be null.";
    private static final String NULL_PASSWORD_MESSAGE = "Password cannot be null.";
    private static final String NULL_AGE_MESSAGE = "Age cannot be null.";
    private static final String SHORT_LOGIN_MESSAGE = "Login must be at least 6 characters.";
    private static final String SHORT_PASSWORD_MESSAGE = "Password must be at least 6 characters.";
    private static final String USER_EXISTS_MESSAGE = "User with this login already exists.";
    private static final String UNDERAGE_MESSAGE = "User must be at least 18 years old.";
    private static final int VALID_AGE = 20;
    private static final int UNDERAGE = 17;
    private static final int NEGATIVE_AGE = -1;

    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear(); // Clear storage before each test
    }

    @Test
    public void register_validUser_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);

        User result = registrationService.register(user);

        assertEquals(user, result);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    public void register_existingLogin_notOk() {
        User user = createUser(EXISTING_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(user); // Add directly to storage

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(USER_EXISTS_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = createUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(SHORT_LOGIN_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = createUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(SHORT_PASSWORD_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_underageUser_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, UNDERAGE);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(UNDERAGE_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = createUser(null, VALID_PASSWORD, VALID_AGE);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(NULL_LOGIN_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = createUser(VALID_LOGIN, null, VALID_AGE);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(NULL_PASSWORD_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_nullAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, null);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(NULL_AGE_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_emptyLogin_notOk() {
        User user = createUser("", VALID_PASSWORD, VALID_AGE);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(SHORT_LOGIN_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_emptyPassword_notOk() {
        User user = createUser(VALID_LOGIN, "", VALID_AGE);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(SHORT_PASSWORD_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_negativeAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, NEGATIVE_AGE);

        InvalidUserDataException exception = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user)
        );
        assertEquals(UNDERAGE_MESSAGE, exception.getMessage());
    }

    @Test
    public void register_edgeCasePasswords_okAndNotOk() {
        assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(createUser("validLogin1", "", VALID_AGE)),
                SHORT_PASSWORD_MESSAGE
        );
        assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(createUser("validLogin2", "abc", VALID_AGE)),
                SHORT_PASSWORD_MESSAGE
        );
        assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(createUser("validLogin3", "abcde", VALID_AGE)),
                SHORT_PASSWORD_MESSAGE
        );
        assertDoesNotThrow(
                () -> registrationService.register(createUser("validLogin4", "abcdef", VALID_AGE))
        );
        assertDoesNotThrow(
                () -> registrationService.register(createUser("validLogin5", "abcdefgh", VALID_AGE))
        );
    }

    @Test
    public void register_edgeCaseLogin_okAndNotOk() {
        assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(
                        createUser("", VALID_PASSWORD + "1", VALID_AGE)
                ),
                SHORT_LOGIN_MESSAGE
        );
        assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(
                        createUser("abc", VALID_PASSWORD + "2", VALID_AGE)
                ),
                SHORT_LOGIN_MESSAGE
        );
        assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(
                        createUser("abcde", VALID_PASSWORD + "3", VALID_AGE)
                ),
                SHORT_LOGIN_MESSAGE
        );
        assertDoesNotThrow(
                () -> registrationService.register(
                        createUser("abcdef", VALID_PASSWORD + "4", VALID_AGE)
                )
        );
        assertDoesNotThrow(
                () -> registrationService.register(
                        createUser("abcdefgh", VALID_PASSWORD + "5", VALID_AGE)
                )
        );
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
