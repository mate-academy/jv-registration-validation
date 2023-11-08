package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User validUser;
    private static final String LOGIN_VALID = "abcdef";
    private static final String LOGIN_BLANK = "      ";
    private static final String LOGIN_0_LENGTH = "";
    private static final String LOGIN_3_LENGTH = "abc";
    private static final String LOGIN_5_LENGTH = "abcde";
    private static final String PASSWORD_BLANK = "      ";
    private static final String PASSWORD_0_LENGTH = "";
    private static final String PASSWORD_3_LENGTH = "pas";
    private static final String PASSWORD_5_LENGTH = "passw";
    private static final String PASSWORD_VALID = "passwo";
    private static final String PASSWORD_8_LENGTH = "password";
    private static final int USER_AGE_MIN = 18;
    private static final int USER_AGE_VALID = 25;
    private static final long USER_ID_VALID = Long.MAX_VALUE;
    private static final int USER_AGE_NOT_VALID = 16;
    private static final int USER_AGE_0_LESS = -16;

    @BeforeAll
    static void init() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
    }

    @BeforeEach
    void setUp() {
        validUser.setLogin(LOGIN_VALID);
        validUser.setPassword(PASSWORD_VALID);
        validUser.setAge(USER_AGE_VALID);
        validUser.setId(USER_ID_VALID);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_validUser_Ok() {
        Storage.people.clear();
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_0_lengthLogin_notOk() {
        validUser.setLogin(LOGIN_0_LENGTH);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_3_lengthLogin_notOk() {
        validUser.setLogin(LOGIN_3_LENGTH);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_5_lengthLogin_notOk() {
        validUser.setLogin(LOGIN_5_LENGTH);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_blankLogin_notOk() {
        validUser.setLogin(LOGIN_BLANK);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_0_lengthPassword_notOk() {
        validUser.setPassword(PASSWORD_0_LENGTH);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_blankPassword_notOk() {
        validUser.setPassword(PASSWORD_BLANK);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_3_lengthPassword_notOk() {
        validUser.setPassword(PASSWORD_3_LENGTH);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_5_lengthPassword_notOk() {
        validUser.setPassword(PASSWORD_5_LENGTH);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_less_0_age_notOk() {
        validUser.setAge(USER_AGE_0_LESS);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_notValidAge_notOk() {
        validUser.setAge(USER_AGE_NOT_VALID);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(validUser));
    }

    @Test
    void register_minimalAllowedAge_ok() {
        Storage.people.clear();
        validUser.setAge(USER_AGE_MIN);
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void register_duplicateLogin_notOk() {
        Storage.people.clear();
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void register_8_lengthPassword_ok() {
        Storage.people.clear();
        validUser.setPassword(PASSWORD_8_LENGTH);
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }
}
