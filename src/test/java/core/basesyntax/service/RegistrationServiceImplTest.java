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
    private static User user1 = new User();
    private static User user2 = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user1.setLogin("U123");
        user1.setPassword("123456");
        user1.setAge(18);
        user2.setLogin("U456");
        user2.setPassword("123456");
        user2.setAge(46);
    }

    @Test
    void register_invalidAge_NotOk() {
        user1.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
        user2.setAge(-19);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        user1.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_theSameUserLogin_notOk() {
        registrationService.register(user2);
        user1.setLogin("U456");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        }, "Should throw an Exception");
    }

    @Test
    void register_nullAge_NotOk() {
        user1.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_validUser_Ok() {
        User actual1 = registrationService.register(user1);
        assertTrue(Storage.people.contains(actual1));
        User actual2 = registrationService.register(user2);
        assertTrue(Storage.people.contains(actual2));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
