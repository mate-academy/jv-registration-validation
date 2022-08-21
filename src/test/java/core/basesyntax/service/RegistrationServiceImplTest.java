package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String VALID_USER_LOGIN = "login";
    private static final String VALID_USER_PASSWORD = "password";
    private static final int VALID_USER_AGE = 18;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User(VALID_USER_LOGIN, VALID_USER_PASSWORD, VALID_USER_AGE);
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(validUser,registrationService.register(validUser));
        assertNotNull(validUser.getId());
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_lessThanMinAge_NotOk() {
        validUser.setAge(VALID_USER_AGE - 1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_negativeAge_NotOk() {
        validUser.setAge(-18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_ageMoreThanMin_Ok() {
        validUser.setAge(46);
        assertEquals(validUser,registrationService.register(validUser));
        assertNotNull(validUser.getId());
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_lessThanMinPassword_NotOk() {
        validUser.setPassword("pass");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_minLengthPassword_Ok() {
        validUser.setPassword("123456");
        assertEquals(validUser,registrationService.register(validUser));
        assertNotNull(validUser.getId());
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_moreThanMinPassword_Ok() {
        validUser.setPassword("123456password654321");
        assertEquals(validUser,registrationService.register(validUser));
        assertNotNull(validUser.getId());
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_UserAlreadyExist_NotOk() {
        registrationService.register(validUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
