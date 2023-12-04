package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User validUser;
    private static final String LOGIN_VALID = "abcdef";
    private static final String LOGIN_0_LENGTH = "";
    private static final String LOGIN_3_LENGTH = "abc";
    private static final String LOGIN_5_LENGTH = "abcde";
    private static final String LOGIN_8_LENGTH = "abcdefgh";
    private static final String PASSWORD_0_LENGTH = "";
    private static final String PASSWORD_3_LENGTH = "pas";
    private static final String PASSWORD_5_LENGTH = "passw";
    private static final String PASSWORD_VALID = "passwo";
    private static final String PASSWORD_8_LENGTH = "password";
    private static final int USER_AGE_MIN = 18;
    private static final int USER_AGE_LESS_BY_ONE_THAN_MIN = USER_AGE_MIN - 1;
    private static final int USER_AGE_VALID = 25;
    private static final long USER_ID_VALID = Long.MAX_VALUE;
    private static final int USER_AGE_NOT_VALID = 16;
    private static final int USER_AGE_0_LESS = -16;
    private static final int USER_AGE_0 = 0;

    @BeforeAll
    static void init() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        validUser.setLogin(LOGIN_VALID);
        validUser.setPassword(PASSWORD_VALID);
        validUser.setAge(USER_AGE_VALID);
        validUser.setId(USER_ID_VALID);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_validUser_Ok() {
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @ParameterizedTest
    @ValueSource(strings = {LOGIN_0_LENGTH, LOGIN_3_LENGTH, LOGIN_5_LENGTH})
    void register_shortLogin_notOk(String login) {
        validUser.setLogin(login);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @ParameterizedTest
    @ValueSource(strings = {LOGIN_VALID, LOGIN_8_LENGTH})
    void register_validLoginLength_ok(String login) {
        validUser.setLogin(login);
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @ParameterizedTest
    @ValueSource(strings = {PASSWORD_0_LENGTH, PASSWORD_3_LENGTH, PASSWORD_5_LENGTH})
    void register_shortLengthPassword_notOk(String password) {
        validUser.setPassword(password);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @ParameterizedTest
    @ValueSource(strings = {PASSWORD_VALID, PASSWORD_8_LENGTH})
    void register_validPasswordLength_ok(String password) {
        validUser.setPassword(password);
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @ParameterizedTest
    @ValueSource(ints = {USER_AGE_0_LESS, USER_AGE_0, USER_AGE_NOT_VALID,
            USER_AGE_LESS_BY_ONE_THAN_MIN})
    void register_notValidAge_notOk(int age) {
        validUser.setAge(age);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @ParameterizedTest
    @ValueSource(ints = {USER_AGE_MIN, USER_AGE_VALID})
    void register_allowedAge_ok(int age) {
        validUser.setAge(age);
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void register_duplicateLogin_notOk() {
        User registeredUser = registrationService.register(validUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(registeredUser));
    }
}
