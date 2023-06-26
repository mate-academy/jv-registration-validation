package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User bob;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        bob = new User();
    }

    @BeforeEach
    void setUp() {
        bob.setLogin("BobLogin");
        bob.setAge(18);
        bob.setPassword("123456");
    }

    @Test
    void register_ageLessThanMin_NotOk() {
        bob.setAge(12);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_loginAlreadyExists_NotOK() {
        registrationService.register(bob);
        bob.setLogin("Bob");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_PasswordLessThanMin_notOk() {
        bob.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void registerLogin_null_notOk() {
        bob.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_nullAge_notOk() {
        bob.setAge(null);;
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        bob.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_validUser_Ok() {
        User test1 = registrationService.register(bob);
        assertTrue(Storage.people.contains(test1));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
