package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String EXISTING_LOGIN = "JohnSmith";
    private static final String VALID_LOGIN = "JohnnyBGoode";
    private static final String THREE_CHARS_LOGIN = "Bad";
    private static final String FIVE_CHARS_LOGIN = "Short";
    private static final String VALID_PASSWORD = "1234567";
    private static final String THREE_CHARS_PASSWORD = "123";
    private static final String FIVE_CHARS_PASSWORD = "12345";
    private static final Integer VALID_AGE = 18;
    private static final Integer LOW_AGE = 17;
    private static final Integer NEGATIVE_AGE = -7;
    private static final int INITIAL_USER_COUNT = 3;
    private static final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();

    private static User generateUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);

        return user;
    }

    @BeforeEach
    void setUp() {
        people.add(generateUser(EXISTING_LOGIN, VALID_PASSWORD, 20));
        people.add(generateUser("SomeUser", VALID_PASSWORD, 25));
        people.add(generateUser("OtherUser", VALID_PASSWORD, 32));
    }

    @AfterEach
    void tearDown() {
        people.clear();
    }

    @Test
    void register_nullUser_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService.register(null));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_nullLogin_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(null, VALID_PASSWORD, VALID_AGE)));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_shortLogin_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(THREE_CHARS_LOGIN, VALID_PASSWORD, VALID_AGE)));
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(FIVE_CHARS_LOGIN, VALID_PASSWORD, VALID_AGE)));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_nullPassword_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(VALID_LOGIN, null, VALID_AGE)));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_shortPassword_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(VALID_LOGIN, THREE_CHARS_PASSWORD, VALID_AGE)));
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(VALID_LOGIN, FIVE_CHARS_PASSWORD, VALID_AGE)));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_nullAge_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(VALID_LOGIN, VALID_PASSWORD, null)));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_lowAge_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(VALID_LOGIN, VALID_PASSWORD, LOW_AGE)));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_negativeAge_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(VALID_LOGIN, VALID_PASSWORD, NEGATIVE_AGE)));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_existingUser_fail() {
        assertThrows(UserRegistrationError.class, () -> registrationService
                .register(generateUser(EXISTING_LOGIN, VALID_PASSWORD, VALID_AGE)));
        assertEquals(INITIAL_USER_COUNT, people.size());
    }

    @Test
    void register_validUser_success() {
        assertDoesNotThrow(() -> registrationService
                .register(generateUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE)));
        assertEquals(INITIAL_USER_COUNT + 1, people.size());
    }
}
