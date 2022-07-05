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
    private static User Bob = new User();
    private static User Alice = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Bob.setLogin("BobLogin");
        Bob.setAge(18);
        Bob.setPassword("123456");
        Alice.setLogin("AliceLogin");
        Alice.setAge(18);
        Alice.setPassword("123456");
    }

    @Test
    void invalidAge_NotOK() {
        Bob.setAge(12);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(Bob);
        });
        Alice.setAge(-22);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(Alice);
        });
    }

    @Test
    void inbalidLogin_NotOK() {
        registrationService.register(Bob);
        Bob.setLogin("Bob");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(Bob);
        });
        registrationService.register(Alice);
        Alice.setLogin("Alice");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(Alice);
        });
    }

    @Test
    void invalidPassword_NotOk() {
        Bob.setPassword("12345");
        Alice.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(Bob);
        });
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(Alice);
        });
    }

    @Test
    void invalidlogin_Null() {
        Bob.setLogin(null);
        Alice.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(Bob);
        });
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(Alice);
        });
    }

    @Test
    void invalidAge_null() {
        Bob.setAge(null);
        Alice.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(Bob);
        });
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(Alice);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        Bob.setPassword(null);
        Alice.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(Bob);
        });
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(Alice);
        });
    }

    @Test
    void containsLogin_Ok() {
        User test1 = registrationService.register(Bob);
        User test2 = registrationService.register(Alice);
        assertTrue(Storage.people.contains(test2));
        assertTrue(Storage.people.contains(test1));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
