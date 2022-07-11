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

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User("user1", "passIsOk", 21);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_NullUser_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_newValidUser_OK() {
        assertTrue(registrationService.register(validUser).equals(validUser));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_passwordLessThanMIn_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("user5", "111", 21));
        });
    }

    @Test
    void register_ageLessThanMin_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("user6", "123456", 15));
        });
    }

    @Test
    void register_UserAlreadyExist_notOK() {
        Storage.people.add(validUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullLogin_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(null, "123456", 18));
        });
    }

    @Test
    void register_UserNullPassword_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("user7", null, 20));
        });
    }

    @Test
    void register_UserNullAge_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("user8", "null123", null));
        });
    }
}
