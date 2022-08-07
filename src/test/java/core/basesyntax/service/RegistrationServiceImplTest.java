package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User VALID_USER_FIRST = new User("Boby", 45, "qwerty123");
    private static final User VALID_USER_SECOND = new User("Bob", 30, "qwerty123");
    private static final User VALID_USER_SAME_AS_SECOND = new User("Bob", 30, "qwerty123");
    private static final User VALID_USER_THIRD = new User("Joy", 22, "qwerty123");
    private static final User VALID_USER_SAME_AS_THIRD_BUT_OTHER_AGE
            = new User("Joy", 26, "qwerty123");
    private static final User VALID_USER_FOURTH
            = new User("Nil", 19, "qwerty789");
    private static final User VALID_USER_SAME_AS_FOURTH_BUT_OTHER_LOGIN
            = new User("Nill", 19, "qwerty789");
    private static final User VALID_LONG_LOGIN_USER
            = new User("Tom Sinisterly Stone", 30, "qwerty123");
    private static final User VALID_ONLY_SYMBOLS_LOGIN_USER = new User("$$$$<>", 26, "qwerty123");
    private static final User VALID_LARGE_AGE_USER = new User("Sara", 99, "qwerty123");
    private static final User INVALID_AGE_USER = new User("Sam", 12, "qwerty123");
    private static final User NULL_LOGIN_USER = new User(null, 44, "qwerty456");
    private static final User VALID_18TH_AGE_USER = new User("Ted", 18, "qwerty123");
    private static final User NULL_AGE_USER
            = new User("Ted", null, "qwerty456");
    private static final User NEGATIVE_AGE_USER = new User("Rick", -7, "qwerty456");
    private static final User VALID_LARGE_PASSWORD_USER
            = new User("Pot", 47, "123456789qwertyuiopp!");
    private static final User INVALID_PASSWORD_USER = new User("Ron", 24, "qwert");
    private static final User VALID_SIX_CHARACTERS_PASSWORD_USER = new User("Bin", 55, "qwerty");
    private static final User VALID_ONLY_DIGITS_PASSWORD_USER = new User("Kang", 46, "123456789");
    private static final User NULL_PASSWORD_USER = new User("Kat", 33, null);
    private static final User NULL_PASSWORD_NULL_LOGIN_NULL_AGE_USER = new User(null, 0, null);
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUserFirst_Ok() {
        registrationService.register(VALID_USER_FIRST);
    }

    @Test
    void register_alreadyExistedUser_notOk() {
        registrationService.register(VALID_USER_SECOND);
        try {
            registrationService.register(VALID_USER_SAME_AS_SECOND);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("Runtime exception expected");
    }

    @Test
    void register_validUserSameAsExistedUserButOtherAge_NotOk() {
        registrationService.register(VALID_USER_THIRD);
        try {
            registrationService.register(VALID_USER_SAME_AS_THIRD_BUT_OTHER_AGE);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("Runtime exception expected");
    }

    @Test
    void register_validUserSameAsExistedUserButOtherLogin_Ok() {
        registrationService.register(VALID_USER_FOURTH);
        registrationService.register(VALID_USER_SAME_AS_FOURTH_BUT_OTHER_LOGIN);
    }

    @Test
    void register_validLongLoginUser_Ok() {
        registrationService.register(VALID_LONG_LOGIN_USER);
    }

    @Test
    void register_validOnlySymbolsLoginUser_Ok() {
        registrationService.register(VALID_ONLY_SYMBOLS_LOGIN_USER);
    }

    @Test
    void register_nullLoginUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(NULL_LOGIN_USER));
    }

    @Test
    void register_validLargeAgeUser_Ok() {
        registrationService.register(VALID_LARGE_AGE_USER);
    }

    @Test
    void register_invalidAgeUser_notOk() {
        try {
            registrationService.register(INVALID_AGE_USER);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("Runtime exception expected");
    }

    @Test
    void register_validUserEighteenthAge_Ok() {
        registrationService.register(VALID_18TH_AGE_USER);
    }

    @Test
    void register_negativeAgeUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(NEGATIVE_AGE_USER));
    }

    @Test
    void register_nullAgeUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(NULL_AGE_USER));
    }

    @Test
    void register_invalidPasswordUser_notOk() {
        try {
            registrationService.register(INVALID_PASSWORD_USER);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("Runtime exception expected");
    }

    @Test
    void register_validUserSixCharactersPassword_Ok() {
        registrationService.register(VALID_SIX_CHARACTERS_PASSWORD_USER);
    }

    @Test
    void register_validUserLargePassword_Ok() {
        registrationService.register(VALID_LARGE_PASSWORD_USER);
    }

    @Test
    void register_validUserOnlyDigitsPassword_Ok() {
        registrationService.register(VALID_ONLY_DIGITS_PASSWORD_USER);
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(NULL_PASSWORD_USER));
    }

    @Test
    void register_nullLoginNullPasswordNullAgeUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(NULL_PASSWORD_NULL_LOGIN_NULL_AGE_USER));
    }
}
