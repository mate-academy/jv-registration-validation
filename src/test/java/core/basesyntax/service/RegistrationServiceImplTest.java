package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl regService;

    @BeforeAll
    static void init() {
        regService = new RegistrationServiceImpl();
    }

    @Test
    void register_loginAlreadyTaken_notOk() {
        User user1 = new User("Alice", "123456", 19);
        User user2 = new User("Alice", "654321", 22);
        assertNotNull(regService.register(user1));
        assertThrows(RuntimeException.class, () -> regService.register(user2));
    }

    @Test
    void register_ageIsLessThanMustBe_notOk() {
        User user = new User("Alice", "123456", 16);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_passwordIsLessThanMustBe_notOk() {
        User user = new User("Alice", "12345", 19);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_validUser_ok() {
        User actual = new User("Bob", "BobSlave123", 19);
        assertEquals(actual, regService.register(actual));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> regService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        User nullAge = new User("Alice", "123456", null);
        assertThrows(RuntimeException.class, () -> regService.register(nullAge));
    }

    @Test
    void register_nullPassword_notOk() {
        User nullPassword = new User("Alice", null, 19);
        assertThrows(RuntimeException.class, () -> regService.register(nullPassword));
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLogin = new User(null, "123456", 19);
        assertThrows(RuntimeException.class, () -> regService.register(nullLogin));
    }

    @Test
    void register_invalidAge_notOk() {
        User invalidAge = new User("Alice", "123456", 1);
        assertThrows(RuntimeException.class, () -> regService.register(invalidAge));
    }
}
