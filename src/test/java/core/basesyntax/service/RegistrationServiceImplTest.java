package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int MINIMUM_ALLOWABLE_AGE = 18;
    private static final String MINIMUM_ALLOWABLE_QNTY_CHARACTERS_PASSWORD = "qwerty";
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_oneValidUser_Ok() {
        registrationService.register(new User("Bobby", 45, "qwerty123"));
    }

    @Test
    void register_twoValidUsers_Ok() {
        registrationService.register(new User("Bobby", 45, "qwerty123"));
        registrationService.register(new User("Kim", 38, "qwerty456"));
    }

    @Test
    void register_alreadyExistedUser_notOk() {
        Storage.people.add(new User("Bob", 30, "qwerty123"));
        try {
            registrationService.register(new User("Bob", 30, "qwerty123"));
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("Runtime exception expected "
                + "when registering user with already existing login");
    }

    @Test
    void register_nullLoginUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(new User(null, 44, "qwerty456")),
                "Runtime exception expected when registering user with login NULL");
    }

    @Test
    void register_validLargeAgeUser_Ok() {
        registrationService.register(new User("Sara", 99, "qwerty123"));
    }

    @Test
    void register_invalidAgeUser_notOk() {
        try {
            registrationService.register(new User("Sam", 12, "qwerty123"));
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("Runtime exception expected when registering user with invalid age");
    }

    @Test
    void register_validUserMinimumAllowableAge_Ok() {
        registrationService.register(new User("Ted", MINIMUM_ALLOWABLE_AGE, "qwerty123"));
    }

    @Test
    void register_negativeAgeUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(new User("Rick", -7, "qwerty456")),
                "Runtime exception expected when registering user with negative age");
    }

    @Test
    void register_nullAgeUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(new User("Ted", null, "qwerty456")),
                "Runtime exception expected when registering user with age NULL");
    }

    @Test
    void register_invalidPasswordUser_notOk() {
        try {
            registrationService.register(new User("Ron", 24, "qwert"));
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("Runtime exception expected "
                + "when registering user with invalid password");
    }

    @Test
    void register_validUserSixCharactersPassword_Ok() {
        registrationService.register(new User("Bin", 55,
                MINIMUM_ALLOWABLE_QNTY_CHARACTERS_PASSWORD));
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(new User("Kat", 33, null)),
                "Runtime exception expected when registering user with password NULL");
    }

    @Test
    void register_nullUser_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> registrationService.register(null),
                "Runtime exception expected when registering user NULL");
    }
}
