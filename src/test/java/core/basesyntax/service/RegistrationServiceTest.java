package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final User NULL_USER = null;
    private static final User VALID_USER = new User("Nazarbello", "Leopold", 18);
    private static final User SHORT_PASSWORD_USER = new User("Nazarbello", "", 18);
    private static final User SHORT_LOGIN_USER = new User("", "Leopold", 18);
    private static final User SMALL_AGE_USER = new User("Nazarbello", "Leopold", 0);

    private static final User NULL_PASSWORD_USER = new User("Arsen", null, 18);
    private static final User NULL_LOGIN_USER = new User(null, "888888", 18);
    private static final User NULL_AGE_USER = new User("Arsen", "888888", null);

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(NULL_USER),
                "It should throw RegistrationException when user is null");
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(NULL_PASSWORD_USER),
                "It should throw RegistrationException when password is null");
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(NULL_LOGIN_USER),
                "It should throw RegistrationException when login is null");
    }

    @Test
    void register_nulAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(NULL_AGE_USER),
                "It should throw RegistrationException when age is null");
    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SHORT_PASSWORD_USER),
                "Password length must be more than "
                        + SHORT_PASSWORD_USER.getPassword().length() + "letters");
        String passwordOneLetter = "a";
        SHORT_PASSWORD_USER.setPassword(passwordOneLetter);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SHORT_PASSWORD_USER),
                "Password length must be more than " + passwordOneLetter.length() + "letters");
        String passwordThreeLetters = "abc";
        SHORT_PASSWORD_USER.setPassword(passwordThreeLetters);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SHORT_PASSWORD_USER),
                "Password length must be more than " + passwordThreeLetters.length() + "letters");
        String passwordFiveLetters = "abcde";
        SHORT_PASSWORD_USER.setPassword(passwordFiveLetters);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SHORT_PASSWORD_USER),
                "Password length must be more than " + passwordFiveLetters.length() + "letters");
    }

    @Test
    void register_shortLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SHORT_LOGIN_USER),
                "Login length must be more than "
                        + SHORT_LOGIN_USER.getLogin().length() + "letters");
        String loginOneLetter = "a";
        SHORT_LOGIN_USER.setLogin(loginOneLetter);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SHORT_LOGIN_USER),
                "Login length must be more than " + loginOneLetter.length() + "letters");
        String loginThreeLetters = "abc";
        SHORT_LOGIN_USER.setLogin(loginThreeLetters);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SHORT_LOGIN_USER),
                "Login length must be more than " + loginThreeLetters.length() + "letters");
        String loginFiveLetters = "abcde";
        SHORT_LOGIN_USER.setLogin(loginFiveLetters);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SHORT_LOGIN_USER),
                "Login length must be more than " + loginFiveLetters.length() + "letters");
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(registrationService.register(VALID_USER), VALID_USER,
                "This method should register valid user: " + VALID_USER);
    }

    @Test
    void register_existingUser_notOk() {
        User existing = new User("AnnaLisa", "12345678", 78);
        registrationService.register(existing);
        assertThrows(RegistrationException.class, () -> registrationService.register(existing),
                "Must be thrown RegistrationException while registrating existing user");
    }

    @Test
    void register_smallAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SMALL_AGE_USER),
                "Your age should be at least 18");
        Integer negativeAge = -1;
        SMALL_AGE_USER.setAge(negativeAge);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SMALL_AGE_USER),
                "Your age must be non negative");
        Integer tenAge = 10;
        SMALL_AGE_USER.setAge(tenAge);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SMALL_AGE_USER),
                "Your age should be at least 18");
        Integer edgeAge = 17;
        SMALL_AGE_USER.setAge(edgeAge);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(SMALL_AGE_USER),
                "Your age should be at least 18");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
