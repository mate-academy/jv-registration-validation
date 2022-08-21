package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    public static final String DEFAULT_LOGIN = "jesus";
    public static final String DEFAULT_PASSWORD = "christ";
    public static final int DEFAULT_AGE = 33;
    private static RegistrationService registrationService;
    private User expectedUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        expectedUser = new User();
        expectedUser.setLogin(DEFAULT_LOGIN);
        expectedUser.setPassword(DEFAULT_PASSWORD);
        expectedUser.setAge(DEFAULT_AGE);
    }

    @Test
    void register_setDefaultUser_Ok() {
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_minimalLoginLength_Ok() {
        expectedUser.setLogin("ab");
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_minimalValidAge_OK() {
        expectedUser.setAge(18);
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_maximumValidAge_Ok() {
        expectedUser.setAge(123);
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_twoDifferentUsers_Ok() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(30);
        registrationService.register(expectedUser);
        registrationService.register(user);
        assertTrue(Storage.people.contains(expectedUser));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        expectedUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        expectedUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_nullAge_NotOt() {
        expectedUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_emptyLogin_NotOk() {
        expectedUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_shortLogin_NotOk() {
        expectedUser.setLogin("a");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_shortPassword_NotOk() {
        expectedUser.setPassword("five5");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_lessMinimalAge_NotOk() {
        expectedUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_moreMaximumAge_NotOk() {
        expectedUser.setAge(124);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_sameLogin_NotOk() {
        registrationService.register(expectedUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
