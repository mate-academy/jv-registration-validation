package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User alice = new User();
    private User bob = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        alice.setLogin("Alice");
        alice.setPassword("123Alice");
        alice.setAge(18);

        bob.setLogin("Bob");
        bob.setPassword("123Bob");
        bob.setAge(22);
    }

    @Test
    void register_ageLessThan18_notOk() {
        alice.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(alice);
        });
        bob.setAge(-1);
        assertThrows(RuntimeException.class,
                () -> {
                    registrationService.register(bob);
                });
    }

    @Test
    void register_passwordLeast6Characters_notOk() {
        bob.setPassword("abcda");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_nullAge_notOk() {
        bob.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void register_theSameLogin_notOk() {
        registrationService.register(alice);
        bob.setLogin("Alice");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        }, "The same login! Exception!");
    }

    @Test
    void register_userName_Ok() {
        User actual1 = registrationService.register(alice);
        assertTrue(Storage.people.contains(actual1));
        User actual2 = registrationService.register(bob);
        assertTrue(Storage.people.contains(actual2));
    }

    @AfterEach
    void register_cleanStorage()
    {
        Storage.people.clear();
    }
}

