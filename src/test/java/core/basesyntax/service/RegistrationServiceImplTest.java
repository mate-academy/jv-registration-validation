package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 45;
    private static final int INVALID_AGE1 = 17;
    private static final int INVALID_AGE2 = 126;
    private static final String VALID_PASSWORD = "qwerty";
    private static final String INVALID_PASSWORD = "1234";
    private static final String LOGIN = "login";
    private static final String LOGIN2 = "login2";
    private RegistrationService registrationService;
    private User userBob;
    private User userAlice;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        userBob = new User();
        userBob.setAge(VALID_AGE);
        userBob.setPassword(VALID_PASSWORD);
        userBob.setLogin(LOGIN);
        userAlice = new User();
        userAlice.setAge(VALID_AGE);
        userAlice.setPassword(VALID_PASSWORD);
        userAlice.setLogin(LOGIN2);
    }

    @Test
    void register_validData_Ok() {
        User actual = registrationService.register(userAlice);
        assertEquals(userAlice, actual);
    }

    @Test
    void register_nullAge_notOk() {
        userBob.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userBob)
        );
    }

    @Test
    void register_invalidAge_notOk() {
        userBob.setAge(INVALID_AGE1);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userBob)
        );
        userBob.setAge(INVALID_AGE2);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userBob)
        );
    }

    @Test
    void register_nullPassword_notOk() {
        userBob.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userBob)
        );
    }

    @Test
    void register_invalidPassword_notOk() {
        userBob.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userBob)
        );
    }

    @Test
    void register_nullLogin_notOk() {
        userBob.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userBob)
        );
    }

    @Test
    void register_userAlreadyExist_notOk() {
        registrationService.register(userBob);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userBob)
        );
    }
}
