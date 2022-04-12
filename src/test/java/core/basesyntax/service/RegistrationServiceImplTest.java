package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registration = new RegistrationServiceImpl();
    private StorageDaoImpl storage;
    private User alice = new User();
    private User bob = new User();
    private User jack = new User();

    @Test
    void addNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registration.register(null);
        });
    }

    @Test
    void addUserWithNullData_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registration.register(alice);
        });
    }

    @Test
    void addUserWithNullLoginAgeOrPassword_NotOk() {
        alice.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registration.register(alice);
        });

        alice.setLogin("Alice");
        assertThrows(RuntimeException.class, () -> {
            registration.register(alice);
        });

        alice.setAge(null);
        alice.setPassword("1234567");
        assertThrows(RuntimeException.class, () -> {
            registration.register(alice);
        });
    }

    @Test
    void addUserWithBadPassword_NotOk() {
        alice.setAge(20);
        alice.setLogin("Alice");
        alice.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registration.register(alice);
        });
    }

    @Test
    void addUserWithSmallAge_NotOk() {
        alice.setAge(10);
        alice.setLogin("Alice");
        alice.setPassword("1234567");
        assertThrows(RuntimeException.class, () -> {
            registration.register(alice);
        });
    }

    @Test
    void addUserWithBadLogin_NotOk() {
        alice.setAge(25);
        alice.setLogin("");
        alice.setPassword("1234567");
        assertThrows(RuntimeException.class, () -> {
            registration.register(alice);
        });
    }

    @Test
    void addUserWithSameLogin_NotOk() {
        storage = new StorageDaoImpl();
        bob.setAge(20);
        bob.setLogin("Bob");
        bob.setPassword("1234567");
        registration.register(bob);
        User actual = storage.get("Bob");
        Assertions.assertEquals(bob, actual);

        jack.setAge(22);
        jack.setLogin("Bob");
        jack.setPassword("1234567");

        assertThrows(RuntimeException.class, () -> {
            registration.register(jack);
        });
    }
}
