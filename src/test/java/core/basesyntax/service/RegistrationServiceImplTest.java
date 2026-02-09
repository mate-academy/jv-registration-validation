package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final String VALID_LOGIN = "LOGGIN";
    private static final String VALID_PASSWORD = "PASSWORD";
    private static final int VALID_AGE = 20;
    private static final String LOGIN_6_CHAR = "loggin";
    private static final String LOGIN_5_CHAR = "login";
    private static final String LOGIN_8_CHAR = "logintwo";
    private static final String LOGIN_3_CHAR = "log";
    private static final String PASSWORD_6_CHAR = "PASSWO";
    private static final String PASSWORD_5_CHAR = "PASSW";
    private static final String PASSWORD_8_CHAR = "PASSWORD";
    private static final String PASSWORD_3_CHAR = "PAS";
    private static final int AGE_15 = 15;
    private static final int AGE_17 = 17;
    private static final int AGE_18 = 18;
    private static final int AGE_20 = 20;
    private static final int NEGATIVE_AGE = -20;


    @BeforeEach
    public void clear() {
        Storage.people.clear();
    }

    @Test
    void register_userAlreadyPresent_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNull_NotOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNotPresent_Ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);

    }

    @Test
    void register_6charLogin_Ok() {
        User user = new User();
        user.setLogin(LOGIN_6_CHAR);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_8charLogin_Ok() {
        User user = new User();
        user.setLogin(LOGIN_8_CHAR);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_5charLogin_NotOk() {
        User user = new User();
        user.setLogin(LOGIN_5_CHAR);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_3charLogin_NotOk() {
        User user = new User();
        user.setLogin(LOGIN_3_CHAR);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_6charPassword_Ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(PASSWORD_6_CHAR);
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_8charPassword_Ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(PASSWORD_8_CHAR);
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_5charPassword_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(PASSWORD_5_CHAR);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_3charPassword_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(PASSWORD_3_CHAR);
        user.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age18_Ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_age20_Ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_20);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_age17_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age15_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
