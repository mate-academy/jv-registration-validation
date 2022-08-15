package core.basesyntax.service;

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
    private static User alice = new User();
    private static User bob = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        alice.setLogin("Alice");
        alice.setPassword("123456");
        alice.setAge(18);
        bob.setLogin("Bob");
        bob.setPassword("123456");
        bob.setAge(46);
    }

    @Test
    void register_invalidAge_NotOk() {
        alice.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(alice);
        });
        bob.setAge(-19);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        alice.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(alice);
        });
    }

    @Test
    void register_theSameUserLogin_notOk() {
        registrationService.register(bob);
        alice.setLogin("Bob");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(alice);
        }, "Should throw an Exception");
    }

    @Test
    void register_nullAge_NotOk() {
        alice.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(alice);
        });
    }

    @Test
    void register_validUser_Ok() {
        User actual1 = registrationService.register(alice);
        assertTrue(Storage.people.contains(actual1));
        User actual2 = registrationService.register(bob);
        assertTrue(Storage.people.contains(actual2));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
