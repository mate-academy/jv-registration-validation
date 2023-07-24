package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String STRING_3_CHARACTER = "abc";
    public static final String STRING_5_CHARACTER = "Ellis";
    public static final String STRING_6_CHARACTER = "Morris";
    public static final String STRING_7_CHARACTER = "Simpson";
    public static final String EMPTY_STRING = "";
    public static final int NEGATIVE_USER_AGE = -21;
    public static final int USER_ZERO_AGE = 0;
    public static final int USER_17_AGE = 17;
    public static final int USER_18_AGE = 18;
    public static final int USER_21_AGE = 21;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User(STRING_7_CHARACTER, STRING_7_CHARACTER, USER_18_AGE);
    }

    @Test
   void register_nullUser_notOk() {
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin(EMPTY_STRING);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword(EMPTY_STRING);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_zeroUserAge_notOk() {
        user.setAge(USER_ZERO_AGE);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_login3Char_notOk() {
        user.setLogin(STRING_3_CHARACTER);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_password3Char_notOk() {
        user.setPassword(STRING_3_CHARACTER);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_login5Char_notOk() {
        user.setLogin(STRING_5_CHARACTER);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_password5Char_notOk() {
        user.setPassword(STRING_5_CHARACTER);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_negativeUserAge_notOk() {
        user.setAge(NEGATIVE_USER_AGE);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_UserSeventeenAge_notOk() {
        user.setAge(USER_17_AGE);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
     void register_addExistUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userWithLogin6Char_Ok() {
        user.setLogin(STRING_6_CHARACTER);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userWithLogin7Char_Ok() {
        user.setLogin(STRING_7_CHARACTER);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userWithPassword6Char_Ok() {
        user.setPassword(STRING_6_CHARACTER);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userWithPassword7Char_Ok() {
        user.setPassword(STRING_7_CHARACTER);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userEighteenthAge_Ok() {
        user.setAge(USER_18_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userTwentyOneAge_Ok() {
        user.setAge(USER_21_AGE);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @AfterEach
     void tearDown() {
        Storage.people.clear();
    }
}
