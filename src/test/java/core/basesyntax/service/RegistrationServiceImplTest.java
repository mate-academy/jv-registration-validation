package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final User NULL_USER = null;
    public static final User USER_WITH_NULL_LOGIN = new User(null, "k!K#F6y97x", 21);
    public static final User USER_WITH_NULL_PASSWORD = new User("Francesco Patterson", null, 20);
    public static final User USER_WITH_NULL_AGE = new User("Jameson Anderson", "px2MF9%-p8", null);
    public static final User USER_WITH_LOGIN_6_CHARACTERS = new User("Harris", "F42u(j(6Ef", 44);
    public static final User USER_WITH_LOGIN_7_CHARACTERS = new User("Griffin", "2i2dZ_i9F;", 24);
    public static final User USER_WITH_LOGIN_5_CHARACTERS = new User("Elian", "7;csyX@64Z", 37);
    public static final User USER_WITH_EMPTY_LOGIN = new User("", "6y#X_hZx27", 22);
    public static final User USER_WITH_PASSWORD_6_CHARACTERS = new User("Nixon Gray", "tjg^3U", 50);
    public static final User USER_WITH_PASSWORD_7_CHARACTERS
            = new User("Philip Anderson", "z;48uiU", 50);
    public static final User USER_WITH_PASSWORD_5_CHARACTERS = new User("Diego Evans", "4t6)&", 29);
    public static final User USER_WITH_EMPTY_PASSWORD = new User("Lukas Rodriguez", "", 49);
    public static final User USER_WITH_ZERO_AGE = new User("Clayton Young", "6S+8(mc4Ku", 0);
    public static final User USER_AGE_18 = new User("Collin Hayes", "6S+8(mc4Ku", 18);
    public static final User USER_AGE_19 = new User("Pierson Lee", "7eLj7Jv(@9", 19);
    public static final User USER_AGE_17 = new User("Jake Barnes", "sjK457_sP@", 17);
    public static final User USER_WITH_NEGATIVE_AGE = new User("Clayton Young", "6S+8(mc4Ku", -20);
    public static final User CORRECT_USER = new User("Oliver Gray", "bGcM@r55~6", 20);
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(NULL_USER));
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_NULL_LOGIN));
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_NULL_PASSWORD));
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_NULL_AGE));
    }

    @Test
    void register_emptyLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_EMPTY_LOGIN));
    }

    @Test
    void register_emptyPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_EMPTY_PASSWORD));
    }

    @Test
    void register_zeroAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_ZERO_AGE));
    }

    @Test
    void register_login5Char_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_LOGIN_5_CHARACTERS));
    }

    @Test
    void register_password5Char_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_PASSWORD_5_CHARACTERS));
    }

    @Test
    void register_negativeAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_WITH_NEGATIVE_AGE));
    }

    @Test
    void register_age17_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(USER_AGE_17));
    }

    @Test
    void register_addExistUser_notOk() {
        registrationService.register(CORRECT_USER);
        assertThrows(RegistrationException.class, () -> registrationService.register(CORRECT_USER));
    }

    @Test
    void register_correctUsers_Ok() {
        registrationService.register(USER_WITH_LOGIN_6_CHARACTERS);
        registrationService.register(USER_WITH_LOGIN_7_CHARACTERS);
        registrationService.register(USER_WITH_PASSWORD_6_CHARACTERS);
        registrationService.register(USER_WITH_PASSWORD_7_CHARACTERS);
        registrationService.register(USER_AGE_18);
        registrationService.register(USER_AGE_19);
    }

    @Test
    void register_multipleCall_Ok() {
        for (int i = 0; i < 1000; i++) {
            registrationService.register(new User("User: " + i, "bGcM@r55~6", 20));
        }
    }
}
