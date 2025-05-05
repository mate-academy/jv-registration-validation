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
    public static final int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User();
        defaultUser.setLogin("login");
        defaultUser.setPassword("password");
        defaultUser.setAge(30);
    }

    @Test
    void register_validData_Ok() {
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void register_emptyLogin_NotOk() {
        defaultUser.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_usersWithSameLogins_NotOk() {
        registrationService.register(defaultUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_lessThenMinAge_NotOk() {
        defaultUser.setAge(MIN_AGE - 1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_moreThenMinAge_Ok() {
        defaultUser.setAge(MIN_AGE + 1);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void register_equalToMinAge_Ok() {
        defaultUser.setAge(MIN_AGE);
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void register_negativeAge_NotOk() {
        defaultUser.setAge(-1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_lessThanMinLengthPassword_NotOk() {
        defaultUser.setPassword("pass1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_equalToMinLengthPassword_Ok() {
        defaultUser.setPassword("pass12");
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void register_moreThanMinLengthPassword_Ok() {
        defaultUser.setPassword("pass123");
        User actualUser = registrationService.register(defaultUser);
        assertEquals(defaultUser, actualUser);
        assertNotNull(actualUser.getId());
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
