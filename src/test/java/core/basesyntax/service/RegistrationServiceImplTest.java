package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int CORRECT_AGE = 20;
    private static final String CORRECT_LOGIN = "3537beget";
    private static final int INVALID_AGE = 12;
    private static final int NEGATIVE_NUMBER = -5;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(24);
        user.setLogin("verbalising123");
        user.setPassword("2443521");
    }

    @Test
    void register_LoginNull_notOK() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeNull_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validAge_Ok() {
        user.setAge(CORRECT_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_validLogin_Ok() {
        user.setLogin(CORRECT_LOGIN);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_AgeLessRequired_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NegativeAge_notOk() {
        user.setAge(NEGATIVE_NUMBER);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectLogin_notOk() {
        user.setLogin("qwe");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginEmpty_notOk() {
        user.setLogin("");

    }

    @Test
    void register_SameLogin_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordEmpty_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordShort_notOk() {
        user.setPassword("bobik");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
