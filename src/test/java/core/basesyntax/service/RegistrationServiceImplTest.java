package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN_FOR_TESTS = "validLogin";
    private static final String DEFAULT_PASSWORD_FOR_TESTS = "validPassword";
    private static final int DEFAULT_AGE_FOR_TESTS = 18;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN_FOR_TESTS);
        user.setPassword(DEFAULT_PASSWORD_FOR_TESTS);
        user.setAge(DEFAULT_AGE_FOR_TESTS);
    }

    @Test
    void register_alreadyRegisteredUser_NotOk() {
        User registeredUser = registrationService.register(user);
        assertRegistrationException(registeredUser);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertRegistrationException(user);
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertRegistrationException(user);
    }

    @Test
    void register_oneLetterLogin_notOk() {
        user.setLogin("s");
        assertRegistrationException(user);
    }

    @Test
    void register_fiveLettersLogin_notOk() {
        user.setLogin("short");
        assertRegistrationException(user);
    }

    @Test
    void register_sixLettersLogin_ok() {
        user.setLogin("normal");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_eightLettersLogin_ok() {
        user.setLogin("isNormal");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertRegistrationException(user);
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertRegistrationException(user);
    }

    @Test
    void register_oneSymbolPassword_notOk() {
        user.setPassword("s");
        assertRegistrationException(user);
    }

    @Test
    void register_fiveSymbolsPassword_notOk() {
        user.setPassword("short");
        assertRegistrationException(user);
    }

    @Test
    void register_sixSymbolsPassword_ok() {
        user.setPassword("normal");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_eightSymbolsPassword_ok() {
        user.setPassword("isNormal");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-33);
        assertRegistrationException(user);
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(0);
        assertRegistrationException(user);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertRegistrationException(user);
    }

    @Test
    void register_lessMinimalAge_notOk() {
        user.setAge(17);
        assertRegistrationException(user);
    }

    @Test
    void register_allowedAge_ok() {
        user.setAge(33);
        assertEquals(user, registrationService.register(user));
    }

    private void assertRegistrationException(User user) {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
}
